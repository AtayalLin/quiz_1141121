	package com.example.quiz_1141121.res;
	
	
	
	public class CreateRes extends BasicRes{
		
		private int questionid; 
		
	
		public CreateRes(int code, String message, int questionid) {
			super(code, message);
			this.questionid = questionid;
		}
	
		public CreateRes() {
			super();
		}
	
		public CreateRes(int code, String message) {
			super(code, message);
		}
	
		public int getQuestionid() {
			return questionid;
		}
	
		public void setQuestionid(int questionid) {
			this.questionid = questionid;
		}
	
		
	
		
	
		
		
	
	}
