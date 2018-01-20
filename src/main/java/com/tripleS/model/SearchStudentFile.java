package com.tripleS.model;

import java.util.Date;

import javax.validation.constraints.Pattern;

import com.tripleS.classLevelConstraints.ValidateSearchStudentFile;
import com.tripleS.enums.FileStatusEnum;

@ValidateSearchStudentFile
public class SearchStudentFile {
	
    private String fileNo;
	
    private FileStatusEnum fileStatus;
    
    @Pattern(regexp = "(^$|[0-9]{10})", message = "*Mobile number must be exactly 10 digits")
    private String mobileNo;
    
    private String firstName;
    
    private String lastName;
	
    private String createdBy;
	
    private String interviewedBy;
	
    private Date createdDate;
	
    private Date interviewedDate;
	
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public FileStatusEnum getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(FileStatusEnum fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getInterviewedBy() {
		return interviewedBy;
	}
	public void setInterviewedBy(String interviewedBy) {
		this.interviewedBy = interviewedBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getInterviewedDate() {
		return interviewedDate;
	}
	public void setInterviewedDate(Date interviewedDate) {
		this.interviewedDate = interviewedDate;
	}
	@Override
	public String toString() {
		return "SearchStudentFile [fileNo=" + fileNo + ", fileStatus=" + fileStatus + ", mobileNo=" + mobileNo
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", createdBy=" + createdBy
				+ ", interviewedBy=" + interviewedBy + ", createdDate=" + createdDate + ", interviewedDate="
				+ interviewedDate + "]";
	}
	
}
