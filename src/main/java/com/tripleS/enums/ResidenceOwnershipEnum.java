package com.tripleS.enums;

public enum ResidenceOwnershipEnum {
	OWNED("Owned by family"),
	RENTED("Rented by family"),
	OTHER("Other");
	
	private final String displayName;
	
	ResidenceOwnershipEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
