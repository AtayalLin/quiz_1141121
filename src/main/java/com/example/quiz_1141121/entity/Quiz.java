package com.example.quiz_1141121.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 對應 MySQL AUTO_INCREMENT
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "type") // 資料庫已更名為 type (問卷類型)	
	private String type;
	
	@Column(name = "intro")
	private String intro;
	
	@Column(name = "start_date")
    private LocalDate start_date;
	
	@Column(name = "end_date")
	private LocalDate end_date;
	
	@Column(name = "is_published")
	private boolean published;
	
	@Column(name = "collect_name")
	private boolean collectName = true;
	
	 @Column(name = "collect_phone")
	 private boolean collectPhone = false;
	 
	 @Column(name = "collect_email")
	 private boolean collectEmail = false;
	 
	 @Column(name = "require_age")
	 private boolean requireAge;
	 
	 	
	public boolean isRequireAge() {
		return requireAge;
	}
	 public void setRequireAge(boolean requireAge) {
		 this.requireAge = requireAge;
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
	public Quiz() {
		super();
	}

	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Quiz(int id, String title, String type, String intro, LocalDate start_date, LocalDate end_date,
			boolean published, boolean collectName, boolean collectPhone, boolean collectEmail, boolean requireAge
			 ) {
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
		this.requireAge = requireAge;
	}
	
	
}
