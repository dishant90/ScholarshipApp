package com.tripleS.enums;

public enum CourseNameEnum {
	SSC("SSC"),
	HSC("HSC"),
	BE("BE"),
	MS("MS"),
	MTECH("MTech"),
	CA("CA");
	
	private final String displayName;
	
	CourseNameEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
