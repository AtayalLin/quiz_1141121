package com.example.quiz_1141121.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz_1141121.constants.ReplyMessage;
import com.example.quiz_1141121.dao.UserDao;
import com.example.quiz_1141121.entity.User;
import com.example.quiz_1141121.req.RegisterReq;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.LoginRes;

/**
 * 會員服務類別 處理 登入、註冊、個人資料更新、密碼修改
 */

@Service
public class UserService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserDao userDao;

	/**
	 * 使用者登入
	 * 
	 * @param email    帳號
	 * @param password 密碼
	 * @return LoginRes 包含登入狀態與用戶資訊
	 */
	/**
	 * 使用者登入邏輯 [新增] 為了對應前端登入按鈕的功能，必須實作登入 API 邏輯
	 */
	public LoginRes login(String email, String password) {
		// 1. 從資料庫取得該 Email 的使用者實體
		User user = userDao.getByEmail(email);

		// 2. 檢查資料庫是否有此使用帳號 (若無，回傳 USER_NOT_FOUND)
		if (user == null) {
			return new LoginRes(ReplyMessage.USER_NOT_FOUND.getCode(), //
					ReplyMessage.USER_NOT_FOUND.getMessage());
		}

		// 2. 比對加密密碼
		if (encoder.matches(password, user.getPassword())) {
			  // 登入成功，將密碼設為空字串後回傳給前端
               //--- [新增] 身份角色判定邏輯 ---
			
              String role = "member"; // 預設角色為一般會員
               if ("test@gmail.com".equals(user.getEmail())) { // [新增] 指定特定帳號為管理員身份       
                 role = "admin";
               }
           // 4. [修正] 安全性處理：回傳前將密碼欄位抹除，防止流向前端
           user.setPassword("");
           
        // 5. [修正] 成功：回傳 code, message, 完整 User 資料, 以及角色類型 (admin/member)
			return new LoginRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage(), user, role);
		} else {
			return new LoginRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(),
					ReplyMessage.USER_PASSWORD_ERROR.getMessage());
		}
	}
	
	 /**
	  * 更新個人資料 (會員中心用)
	  */
	
	public BasicRes updateProfile(User user) {
		   try {
		   // 這裡調用您 UserDao 的更新方法， 呼叫 DAO 執行更新，包含姓名、電話、年齡、頭像欄位
		   userDao.updateUserInfo(user.getEmail(), user.getName(), user.getPhone(), user.getAge(),   
		   user.getAvatar());
		   return new BasicRes(ReplyMessage.SUCCESS.getCode(), "資料更新成功");
		       } 
		      catch (Exception e) 
		       {
		     return new BasicRes(ReplyMessage.SERVICE_ERROR.getCode(), //
		    		 "更新失敗: " + e.getMessage());
		       }
		   }
	
             /**
             * 修改密碼 (對應會員中心 - 安全設定)
             * [新增] 包含舊密碼驗證邏輯，確保安全性
             */
           public BasicRes changePassword(String email, String oldPwd, String newPwd) {
                User user = userDao.getByEmail(email);
       
               // 1. [新增] 驗證舊密碼是否正確
                if (user == null || !encoder.matches(oldPwd, user.getPassword())) {
                    return new BasicRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(), "原密碼輸入錯誤");       
                }
       
                // 2. [新增] 將新密碼加密後更新至資料庫
                try {
                    String encryptedPwd = encoder.encode(newPwd);
                   userDao.updatePassword(email, encryptedPwd);
                    return new BasicRes(ReplyMessage.SUCCESS.getCode(), "密碼修改成功");
               } catch (Exception e) {
                    return new BasicRes(ReplyMessage.SERVICE_ERROR.getCode(), "密碼更新失敗");
               }
          }
           
	/*
	 * 使用者註冊邏輯 新增 user
	 */
	public BasicRes register(RegisterReq req) {
		/* 參數檢查 */
		BasicRes res = checkParams(req);
		if (res != null) {
			return res;
		}
		/* 檢查 email 是否已存在於 DB */
		if (userDao.getEmailCount(req.getEmail()) == 1) {
			return new BasicRes(ReplyMessage.USER_EMAIL_EXISTED.getCode(), //
					ReplyMessage.USER_EMAIL_EXISTED.getMessage());
		}
		/* 新增: 要把密碼加密 */
		try {
			userDao.insert(req.getEmail(), req.getName(), encoder.encode(req.getPassword()), //
					req.getPhone(), req.getAge()); //
		} catch (Exception e) {
			e.printStackTrace();
			return new BasicRes(ReplyMessage.SERVICE_ERROR.getCode(), "伺服器異常，註冊失敗");
		}
		return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
	
	

	private BasicRes checkParams(RegisterReq req) {
		if (!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ReplyMessage.USER_EMAIL_ERROR.getCode(), //
					ReplyMessage.USER_EMAIL_ERROR.getMessage());

		}
		if (!StringUtils.hasText(req.getPassword())) {
			return new BasicRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(), //
					ReplyMessage.USER_PASSWORD_ERROR.getMessage());

		}
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ReplyMessage.USER_NAME_ERROR.getCode(), //
					ReplyMessage.USER_NAME_ERROR.getMessage());

		}
		if (req.getAge() < 18) {
			return new BasicRes(ReplyMessage.USER_AGE_ERROR.getCode(), //
					ReplyMessage.USER_AGE_ERROR.getMessage());
		}
		return null;
	}
}
