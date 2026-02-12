package com.example.quiz_1141121.res;

import java.util.List;

import com.example.quiz_1141121.entity.Questions;

public class GetQuestionRes extends BasicRes{

	private List<Questions> questionList;

	public GetQuestionRes() {
		super();
	}

	public GetQuestionRes(int code, String message) {
		super(code, message);
	}

	public GetQuestionRes(List<Questions> questionList) {
		super();
		this.questionList = questionList;
	}


	public GetQuestionRes(int code, String message, List<Questions> questionList) {
		super(code, message);
		this.questionList = questionList;
	}

	public List<Questions> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Questions> questionList) {
		this.questionList = questionList;
	}
	
	
}
