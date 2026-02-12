package com.example.quiz_1141121.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Column(name = "email")
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "phome")
	private String phome;

	@Column(name = "password")
	private String password;

	@Column(name = "age")
	private int age;

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
		return phome;
	}

	public void setPhome(String phome) {
		this.phome = phome;
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

}
