package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.ResidenceTypeEnum;

public class ResidenceTypeEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(ResidenceTypeEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String residenceType){
		ResidenceTypeEnum residenceTypeEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(residenceType)){
			logger.info("Validation error: no option selected from residence type dropdown");
			residenceTypeEnum = null;
			this.setValue(residenceTypeEnum);
		} else if(ResidenceTypeEnum.valueOf(residenceType) instanceof ResidenceTypeEnum){
			logger.info("Residence type dropdown validation passed; " + residenceType + " is a valid residence type");
			this.setValue(ResidenceTypeEnum.valueOf(residenceType));
		} else {
			logger.info(residenceType + ": No such residence type defined in the code");
		}
		
	}
}
