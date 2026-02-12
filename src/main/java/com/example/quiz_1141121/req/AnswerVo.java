package com.example.quiz_1141121.req;

import com.example.quiz_1141121.entity.Questions;

/* 一個 AnswerVo 表示一題問題的所有資訊以及答案 */
public class AnswerVo {

	private Questions question;
	
//	/* 選項編號 */
//	private int number;
	
	/* 答案、也是選項 */
	private String answer;
	
	

	public AnswerVo(Questions question, String answer) {
	super();
	this.question = question;
	this.answer = answer;
}

	public AnswerVo() {
		super();
	}

	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}

//	public int getNumber() {
//		return number;
//	}
//
//	public void setNumber(int number) {
//		this.number = number;
//	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
}
