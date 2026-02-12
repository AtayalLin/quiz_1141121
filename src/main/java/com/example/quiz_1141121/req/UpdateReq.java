package com.example.quiz_1141121.req;

public class UpdateReq extends CreateReq{
	
	private int quizId;
	public UpdateReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UpdateReq(int quizId) {
		super();
		this.quizId = quizId;
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	
	
}
