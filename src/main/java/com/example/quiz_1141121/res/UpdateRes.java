package com.example.quiz_1141121.res;

/*單純只是用在 update 的方法而創建的Class 功能跟CreateRes 為了怕自己看不懂updatereq為何用CreateRes而創的*/
public class UpdateRes extends CreateRes{
	public UpdateRes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UpdateRes(int code, String message, int questionId) {
		super(code, message, questionId);
		// TODO Auto-generated constructor stub
	}
	public UpdateRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}
}

