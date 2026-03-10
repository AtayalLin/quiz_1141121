package com.example.quiz_1141121.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial") // 此為無視黃蚯蚓
public class FillinId implements Serializable {

	private int quizId;

	private int questionId;

	private String userEmail;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public FillinId(int quizId, int questionId, String userEmail) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.userEmail = userEmail;
	}

	public FillinId() {
		super();
	}

	// 4. [核心重點] 覆寫 hashCode：將複合欄位轉換為雜湊碼
	@Override
	public int hashCode() {
		return Objects.hash(questionId, quizId, userEmail);
	}

	// 5. [核心重點] 覆寫 equals：比較兩個 FillinId 是否指向同一筆資料
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FillinId other = (FillinId) obj;
		return questionId == other.questionId && quizId == other.quizId && Objects.equals(userEmail, other.userEmail);
	}

}
