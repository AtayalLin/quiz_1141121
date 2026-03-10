package com.example.quiz_1141121.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz_1141121.entity.User;


@Repository
public interface UserDao extends JpaRepository<User, String>{

	
    /**
  * 新增使用者 (註冊)
  * [修正] 欄位名稱由 phome 改為 phone 配合 MySQL 改名
  */
	
	@Modifying
	@Transactional
	@Query(value = "insert into user (email, name, password, phone, age) " //
			+ " values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	public void insert(String email, String name, String password, String phone, int age);
	
	/* 取得特定 email 的字數 --> 檢查 email 是否已存在 --> 檢查 email 是否已被註冊過 
	 * count(欄位名稱) */
	@Query(value = "select count(email) from user where email = ?1", nativeQuery = true)
	public int getEmailCount(String userEamil);
	
	@Query(value = "select * from user where email = ?1", nativeQuery = true)
	public User getByEmail(String email);
	
	/**
	* 更新個人資料 (會員中心 - 帳號設定)
	* [新增] 加上 @Modifying 與 @Transactional，並補上 UPDATE SQL
    */
	@Modifying
	@Transactional
	@Query(value = "update user set name = ?2, phone = ?3, age = ?4, avatar = ?5 " //
			+ " where email = ?1", nativeQuery = true)
	public void updateUserInfo(String email, String name, String phone, int age, String avater);

	
	/**
	  * 更新密碼 (會員中心 - 安全設定)
	  * [新增] 加上 @Modifying 與 @Transactional，並補上 UPDATE SQL
	  */
	@Modifying
	@Transactional
	@Query(value = "update user set password = ?2 where email = ?1 ", nativeQuery = true)
	public void updatePassword(String email, String encryptedPwd);
	
	
	@Modifying
	@Transactional
	@Query(value = "update user set avatar = ?2 where email = ?1", nativeQuery = true)
	public void updateAvatar(String email, String avatar);
	
}
