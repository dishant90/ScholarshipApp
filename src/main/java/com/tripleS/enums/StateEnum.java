package com.tripleS.enums;

public enum StateEnum {
	MAHARASHTRA("Maharashtra"),
	RAJASTHAN("Rajasthan"),
	TAMILNADU("Tamil Nadu"),
	GUJARAT("Gujarat");
	
	private final String displayName;
	
	StateEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
