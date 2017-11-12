package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tripleS.enums.CountryEnum;

public class CountryEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(CountryEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String country){
		CountryEnum countryEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(country)){
			logger.info("Validation error: no option selected from Country dropdown");
			countryEnum = null;
			this.setValue(countryEnum);
		} else if(CountryEnum.valueOf(country) instanceof CountryEnum){
			logger.info("Country dropdown validation passed; " + country + " is a valid country");
			this.setValue(CountryEnum.valueOf(country));
		} else {
			logger.info(country + ": No such country defined in the code");
		}
		
	}
}
