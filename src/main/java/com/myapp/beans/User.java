package com.myapp.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class User {
	
	@NotNull
	@Size(min=3, max=20)
	private String username;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(min=3, max=20)
	private String password;
	@NotNull
	@Size(min=3, max=20)
	private String firstName;
	@NotNull
	@Size(min=3, max=20)
	private String lastName;
	@NotNull
	@Size(min=10,max=10)
	private String phone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
