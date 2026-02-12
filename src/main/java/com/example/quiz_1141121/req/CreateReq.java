package com.example.quiz_1141121.req;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz_1141121.entity.Questions;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty; // 新增：用於對齊前端變數名

import jakarta.persistence.Column;

public class CreateReq {

	private String title;

	private String description;

	// 修正：使用 JsonFormat 確保前端字串能轉為 LocalDate
	// 修正：使用 JsonProperty 對齊 Angular 的 startDate (小駝峰)

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("startDate")
	private LocalDate start_date;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("endDate")
	private LocalDate end_date;

	// 修正：對齊前端 Angular 的 publishStatus 邏輯
	@JsonProperty("is_published")
	private boolean published;

	// 修正：變數名稱改為 questionsList，建議與前端陣列名稱保持一致
	@JsonProperty("questionsList")
	private List<Questions> questionList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public List<Questions> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Questions> questionList) {
		this.questionList = questionList;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
