package com.tripleS.enums;

public enum CountryEnum {
	INDIA("India"),
	AUSTRALIA("Australia"),
	UK("UK"),
	USA("USA");
	
	private final String displayName;
	
	CountryEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
