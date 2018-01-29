package com.tripleS.enums;

public enum AcademicYearEnum {
	AY2007_08("2007/08"),
	AY2008_09("2008/09"),
	AY2009_10("2009/10"),
	AY2010_11("2010/11"),
	AY2011_12("2011/12"),
	AY2012_13("2012/13"),
	AY2013_14("2013/14"),
	AY2014_15("2014/15"),
	AY2015_16("2015/16"),
	AY2016_17("2016/17"),
	AY2017_18("2017/18"),
	AY2018_19("2018/19"),
	AY2019_20("2019/20"),
	AY2020_21("2020/21"),
	AY2021_22("2021/22"),
	AY2022_23("2022/23"),
	AY2023_24("2023/24"),
	AY2024_25("2024/25"),
	AY2025_26("2025/26");
	
	private final String displayName;
	
	AcademicYearEnum(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
