package com.tripleS.enums;

public enum RoleEnum {
	APPLICANT("Applicant"),
	FIRST_INTERVIEWER("First Interviewer"),
	FILE_VERIFIER("File Verifier"),
	APPLICATION_VERIFIER("Application Verifier"),
	FAMILY_BACKGROUND_ENQUIRER("Family Background Enquirer"),
	LOAN_APPROVER("Loan Approver"),
	TRUSTEE("Trustee"),
	SYSTEM_ADMINISTRATOR("System Administrator"),
	GENERAL_ADMINISTRATOR("General Adminstrator"),
	DATA_ENTRY_OPERATOR("Data Entry Operator"),
	FINANCE_MANAGER("Finance Manager");
	
	private final String displayName;
	
	RoleEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
