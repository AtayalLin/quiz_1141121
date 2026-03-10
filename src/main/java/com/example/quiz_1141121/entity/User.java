package com.example.quiz_1141121.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "password")
	private String password;

	@Column(name = "age")
	private int age;
	
	@Column(name = "avatar")
	private String avatar; 
	
	 @Column(name = "join_date", insertable = false, updatable = false)
	 private LocalDate joinDate; // [新增]

	 
	 
	public LocalDate getJoinDate() {
		return joinDate;
	}

	 public void setJoinDate(LocalDate joinDate) {
		 this.joinDate = joinDate;
	 }

	 public void setPhone(String phone) {
		 this.phone = phone;
	 }

	 public void setAvatar(String avatar) {
		 this.avatar = avatar;
	 }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAvatar() {
		return avatar;
	}

	public User(String email, String name, String phone, String password, int age, String avatar, LocalDate joinDate) {
		super();
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.age = age;
		this.avatar = avatar;
		this.joinDate = joinDate;
	}

	public User() {
		super();
	}
	
	

}
