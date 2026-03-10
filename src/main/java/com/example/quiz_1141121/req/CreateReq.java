package com.example.quiz_1141121.req;
	
import java.time.LocalDate;
import java.util.List;

import com.example.quiz_1141121.constants.ValidationMsg;
import com.example.quiz_1141121.entity.Questions;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty; // 新增：用於對齊前端變數名

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
	
//	import jakarta.persistence.Column;
	
	public class CreateReq {
	
		private int id;
		
		/* @NotBlank:限制屬性不能是 
		 * 1. 空字串
		 * 2. 全空白字串
		 * 3. null
		 *  message 是指當屬性值違反限制時得到的訊息，等號後面的值必須是常數(final)*/
		@NotBlank(message = ValidationMsg.TITLE_ERROR)
		private String title;
	
		private String type;
	    
		@NotBlank(message = ValidationMsg.DESCRIPTION_ERROR)
	    @JsonProperty("description")
	    private String intro;
	
		// 修正：使用 JsonFormat 確保前端字串能轉為 LocalDate
		// 修正：使用 JsonProperty 對齊 Angular 的 startDate (小駝峰)
	
		@NotNull(message = ValidationMsg.START_DATE_ERROR)
		@JsonFormat(pattern = "yyyy-MM-dd")
		@JsonProperty("startDate")
		private LocalDate start_date;
	
		@NotNull(message = ValidationMsg.END_DATE_ERROR)
		@JsonFormat(pattern = "yyyy-MM-dd")
		@JsonProperty("endDate")
		private LocalDate end_date;
	
		// 修正：對齊前端 Angular 的 publishStatus 邏輯
		@JsonProperty("is_published")
		private boolean published;
		
		 // --- [新增] 對接前端的個資收集開關 ---
		@JsonProperty("collectName")
		private boolean collectName;
		
		@JsonProperty("collectPhone")
		private boolean collectPhone;
		
		@JsonProperty("collectEmail")
		private boolean collectEmail;
		
		@JsonProperty("requireAge")
		private boolean requireAge;
	
		// 修正：變數名稱改為 questionsList，建議與前端陣列名稱保持一致
		/* 嵌套驗證: 對自定義的物件(class)中的屬性 
		 * 	@Valid: 為了讓嵌套驗證中的屬性限制生效，就是 Question 中的屬性限制*/
		@Valid
		@NotEmpty(message = ValidationMsg.QUESTION_LIST_IS_EMPTY)
		@JsonProperty("questionsList")
		private List<Questions> questionList;
		
		public CreateReq() {
	    }
	
		public CreateReq(int id, @NotBlank(message = "Title Error!!") String title, String type,
				@NotBlank(message = "Description Error!!") String intro,
				@NotNull(message = "Start Date Error !!") LocalDate start_date,
				@NotNull(message = "End Date Error !!") LocalDate end_date, boolean published, boolean collectName,
				boolean collectPhone, boolean collectEmail,
				@Valid @NotEmpty(message = "Question List Is Empty !!") List<Questions> questionList) {
			super();
			this.id = id;
			this.title = title;
			this.type = type;
			this.intro = intro;
			this.start_date = start_date;
			this.end_date = end_date;
			this.published = published;
			this.collectName = collectName;
			this.collectPhone = collectPhone;
			this.collectEmail = collectEmail;
			this.questionList = questionList;
		}

		public boolean isCollectName() {
			return collectName;
		}



		public void setCollectName(boolean collectName) {
			this.collectName = collectName;
		}



		public boolean isCollectPhone() {
			return collectPhone;
		}



		public void setCollectPhone(boolean collectPhone) {
			this.collectPhone = collectPhone;
		}



		public boolean isCollectEmail() {
			return collectEmail;
		}



		public void setCollectEmail(boolean collectEmail) {
			this.collectEmail = collectEmail;
		}



		public void setId(int id) {
			this.id = id;
		}
		

		public int getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}
	
		public void setTitle(String title) {
			this.title = title;
		}
	
	
		public String getType() {
			return type;
		}
	
		public void setType(String type) {
			this.type = type;
		}
	
		public String getIntro() {
			return intro;
		}
	
		public void setIntro(String intro) {
			this.intro = intro;
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

		public boolean isRequireAge() {
			return requireAge;
		}

		public void setRequireAge(boolean requireAge) {
			this.requireAge = requireAge;
		}

		
	
		
	}
