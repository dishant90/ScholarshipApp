package com.tripleS.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO {
	
	@NotEmpty(message = "*Please provide your first name")
    private String firstName;
	
	@NotEmpty(message = "*Please provide your last name")
    private String lastName;
	
	@Email(message = "*Please provide a valid email address")
	@NotEmpty(message = "*Please provide your email address")
    private String emailID;
	
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

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

}
