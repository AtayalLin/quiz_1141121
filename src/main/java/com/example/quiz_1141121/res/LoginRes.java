	package com.example.quiz_1141121.res;

import com.example.quiz_1141121.entity.User;

public class LoginRes extends BasicRes {

	private User user;

	private String role; // "admin" 或 "member"

	public LoginRes() {
		super();
	}

	
	public LoginRes(User user, String role) {
		super();
		this.user = user;
		this.role = role;
	}



	// 失敗時使用
	public LoginRes(int code, String message) {
		super(code, message);
	} // 成功時使用 (傳入 User 物件與判定後的角色)
	

	public LoginRes(int code, String message, User user, String role) {
	            super(code, message);
	            this.user = user;
	            this.role = role;
	        }

	public User getUser() {
	            return user;
	        }

	public void setUser(User user) {
	            this.user = user;
	        }

	public String getRole() {
	            return role;
	        }

	public void setRole(String role) {
	            this.role = role;
	       }

}
