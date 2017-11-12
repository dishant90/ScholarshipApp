package com.tripleS.enums;

public enum GenderEnum {
	MALE("Male"),
	FEMALE("Female"),
	OTHER("Other");
	
	private final String displayName;
	
	GenderEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
