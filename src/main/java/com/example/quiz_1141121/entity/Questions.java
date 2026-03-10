	package com.example.quiz_1141121.entity;
	
	import com.example.quiz_1141121.constants.ValidationMsg;
	
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.Id;
	import jakarta.persistence.IdClass;
	import jakarta.persistence.Table;
	import jakarta.validation.constraints.Min;
	import jakarta.validation.constraints.NotBlank;
	
	@Entity
	@Table(name = "questions")
	@IdClass(QuestionId.class)
	public class Questions {
	
		@Id
		@Column(name = "quiz_id")
		private int quiz_id;
		
		@Min(value = 1, message = ValidationMsg.QUESTION_ID_ERROR)
		@Id
		@Column(name = "question_id")
		private int question_id;
		
		@NotBlank(message = ValidationMsg.QUESTION_ERROR)
		@Column(name = "question")
		private String question;
		
		@Column(name = "type")
		private String type;
		
		@Column(name = "is_required")
		private boolean required;
		
		@Column(name = "options")
		private String options;
		
		@Column(name = "is_dependent")
		private boolean is_dependent;
		
		@Column(name = "parent_id")
		private Integer parent_id;
		
		
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
		public boolean isIs_dependent() {
			return is_dependent;
		}
		public void setIs_dependent(boolean is_dependent) {
			this.is_dependent = is_dependent;
		}
		public Integer getParent_id() {
			return parent_id;
		}
		public void setParent_id(Integer parent_id) {
			this.parent_id = parent_id;
		}
	
	}
