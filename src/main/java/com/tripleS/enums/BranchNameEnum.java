package com.tripleS.enums;

public enum BranchNameEnum {
	SCIENCE("Science"),
	ARTS("Arts"),
	Commerce("Commerce"),
	INFORMATION_TECHNOLOGY("Information Technology"),
	COMPUTER_SCIENCE("Computer Science"),
	MECHANICAL("Mechanical"),
	ELECTRONICS("Electronics"),
	INSTRUMENTATION("Instrumentation"),
	NA("Not Applicable");
	
	private final String displayName;
	
	BranchNameEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
