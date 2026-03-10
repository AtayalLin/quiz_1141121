package com.example.quiz_1141121.controller;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;// 新增，處理跨域
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1141121.constants.ReplyMessage;
import com.example.quiz_1141121.entity.User;
import com.example.quiz_1141121.req.RegisterReq;
import com.example.quiz_1141121.res.LoginRes;
import com.example.quiz_1141121.service.UserService;
import com.example.quiz_1141121.req.CreateReq;
import com.example.quiz_1141121.req.DeleteReq;
import com.example.quiz_1141121.req.FeedbackReq;
import com.example.quiz_1141121.req.FillinReq;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.CreateRes;
import com.example.quiz_1141121.res.FeedbackRes;
import com.example.quiz_1141121.res.GetFeedbackUserRes;
import com.example.quiz_1141121.res.GetQuestionRes;
import com.example.quiz_1141121.res.GetQuizRes;
import com.example.quiz_1141121.res.UpdateRes;
import com.example.quiz_1141121.service.FillinService;
import com.example.quiz_1141121.service.QuizService;

@RestController
//允許 Angular (通常是 4200 埠) 進行跨域請求
@CrossOrigin(origins = "*")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private FillinService fillinService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private com.example.quiz_1141121.dao.UserDao userDao;

	/**
	 * [新增] 登入介面 對應前端：Login Modal 的登入按鈕
	 */
	@PostMapping("/quiz/login")
	public LoginRes login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
		return userService.login(email, password);
	}

	/**
	 * [修正] 註冊介面 對應前端：註冊頁面的送出按鈕
	 */
	@PostMapping("/quiz/register")
	public BasicRes register(@RequestBody RegisterReq req) {
		return userService.register(req);
	}

	/**
	 * [新增] 更新個人資料 對應前端：會員中心 - 帳號設定 - 儲存變更
	 */
	@PostMapping("/quiz/update_profile")
	public BasicRes updateProfile(@RequestBody User user) {
		return userService.updateProfile(user);
	}

	/**
	 * [新增] 修改密碼 對應前端：會員中心 - 安全設定 - 修改密碼
	 */
	@PostMapping("/quiz/change_password")
	public BasicRes changePassword(@RequestParam(name = "email") String email, @RequestParam(name = "oldPwd") String oldPwd, @RequestParam(name = "newPwd") String newPwd)
			 {
		return userService.changePassword(email, oldPwd, newPwd);
	}

	/**
	 * 新增問卷與問題 URL: POST http://localhost:8080/quiz/create
	 */

	/* @Valid: 讓 CreateReq 中的屬性限制生效 */
	@PostMapping("quiz/create")
	public CreateRes create(@Valid @RequestBody CreateReq req) {
		return quizService.create(req);
	}

	/**
	 * 更新問卷與問題 (補上這個 API)
	 */
	@PostMapping("quiz/update")
	public UpdateRes update(@Valid @RequestBody CreateReq req) {
		return quizService.update(req);
	}

	/**
	 * 取得所有問卷清單 (不含題目) URL: GET http://localhost:8080/quiz/getAll
	 */
	@GetMapping("quiz/getAll")
	public GetQuizRes getQuizList() {
		return quizService.getQuizList();
	}

	/* API 的路徑: http://localhost:8080/quiz/get_questions_List?quizId=1 */
	@GetMapping("quiz/get_questions_List")
	public GetQuestionRes getQuestionList(@RequestParam("quizId") int quizId) {
		return quizService.getQuestionList(quizId);
	}

	/**
	 * 單筆刪除問卷 (保留你原本的需求) URL: GET http://localhost:8080/quiz/delete_single?quizId=1
	 */
	@GetMapping("quiz/delete_single")
	public BasicRes deleteSingle(@RequestParam("quizId") int quizId) {
		// 將單個 ID 轉為 List 後傳給 Service
		return quizService.deleteQuiz(Arrays.asList(quizId));
	}

	/**
	 * 批次刪除問卷 URL: POST http://localhost:8080/quiz/delete
	 */
	@PostMapping("quiz/delete")
	public BasicRes deleteBatch(@RequestBody DeleteReq req) {
		// 調用 Service 的 deleteQuiz，並傳入 ID 列表
		return quizService.deleteQuiz(req.getQuizIdList());
	}

	@PostMapping("quiz/fillin")
	public BasicRes fillin(@RequestBody FillinReq req) {
		return fillinService.fillin(req);
	}

	@GetMapping("quiz/getAllFillinUsers")
	public GetFeedbackUserRes getAllFillinUsers(@RequestParam("quizId") int quizId) {
		return fillinService.getAllFillinUsers(quizId);
	}

	@PostMapping("quiz/feedback")
	public FeedbackRes feedback(@RequestBody FeedbackReq req) {
		return fillinService.feedback(req);
	}
	
	@PostMapping("quiz/unpublish")
	public BasicRes unpublish(@RequestParam(name = "id") int id) {
	    return quizService.unpublish(id);
	}

	@PostMapping("quiz/publish")
	public BasicRes publish(@RequestParam(name = "id") int id) {
	    return quizService.publish(id);
	}
	
	@GetMapping("quiz/get_history")
	public GetQuizRes getHistory(@RequestParam(name = "email") String email) {
	    List<com.example.quiz_1141121.entity.Quiz> list = fillinService.getUserHistory(email);
	    return new GetQuizRes(
	        com.example.quiz_1141121.constants.ReplyMessage.SUCCESS.getCode(),
	        com.example.quiz_1141121.constants.ReplyMessage.SUCCESS.getMessage(),
	        list
	    );
	}

	@PostMapping("quiz/upload_avatar")
	public BasicRes uploadAvatar(
	        @RequestParam(name = "file") org.springframework.web.multipart.MultipartFile file,
	        @RequestParam(name = "email") String email) {
	    try {
	        String base64 = "data:" + file.getContentType() + ";base64," +
	            java.util.Base64.getEncoder().encodeToString(file.getBytes());
	        userDao.updateAvatar(email, base64);
	        return new BasicRes(
	            com.example.quiz_1141121.constants.ReplyMessage.SUCCESS.getCode(),
	            "頭像更新成功");
	    } catch (Exception e) {
	        return new BasicRes(500, "頭像更新失敗：" + e.getMessage());
	    }
	}
	

}
