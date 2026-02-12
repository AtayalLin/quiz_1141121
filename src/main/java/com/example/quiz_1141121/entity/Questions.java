package com.example.quiz_1141121.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
@IdClass(QuestionId.class)
public class Questions {

	@Id
	@Column(name = "quiz_id")
	private int quiz_id;
	
	@Id
	@Column(name = "question_id")
	private int question_id;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "is_required")
	private boolean required;
	
	@Column(name = "options")
	private String options;
	
	
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
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	
	
	
}
