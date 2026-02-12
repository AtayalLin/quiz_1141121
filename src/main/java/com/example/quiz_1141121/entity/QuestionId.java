package com.example.quiz_1141121.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class QuestionId implements Serializable {

	
	private int quiz_id;

	private int question_id;

	protected QuestionId(int quiz_id, int question_id) {
		this.quiz_id = quiz_id;
		this.question_id = question_id;
	}

	protected QuestionId() {
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionId that = (QuestionId) o;
        return quiz_id == that.quiz_id && question_id == that.question_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quiz_id, question_id);
    }

	public int getQuiz_id() {
		return quiz_id;
	}

	public void setQuiz_id(int quiz_id) {
		this.quiz_id = quiz_id;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
    
    
	
	
}
