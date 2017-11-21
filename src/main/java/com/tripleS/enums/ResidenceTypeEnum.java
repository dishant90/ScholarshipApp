package com.tripleS.enums;

public enum ResidenceTypeEnum {
	APARTMENT("Apartment"),
	BARACK("Barack"),
	BUNGALOW("Bungalow"),
	CHAWL("Chawl"),
	ONPLOT("On-plot");
	
	private final String displayName;
	
	ResidenceTypeEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
