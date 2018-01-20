package com.tripleS.enums;

public enum FileStatusEnum {
	NEW("New"),
	SUBMITTED("Submitted"),
	CLOSED("Closed");
	
	private final String displayName;
	
	FileStatusEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
