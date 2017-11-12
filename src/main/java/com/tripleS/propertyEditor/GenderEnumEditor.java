package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.GenderEnum;

public class GenderEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(GenderEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String gender){
		GenderEnum genderEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(gender)){
			logger.info("Validation error: no option selected from Gender dropdown");
			genderEnum = null;
			this.setValue(genderEnum);
		} else if(GenderEnum.valueOf(gender) instanceof GenderEnum){
			logger.info("Gender dropdown validation passed; " + gender + " is a valid gender");
			this.setValue(GenderEnum.valueOf(gender));
		} else {
			logger.info(gender + ": No such gender defined in the code");
		}
		
	}
}
