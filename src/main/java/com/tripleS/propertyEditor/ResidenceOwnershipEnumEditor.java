package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.ResidenceOwnershipEnum;

public class ResidenceOwnershipEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(ResidenceOwnershipEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String residenceOwnership){
		ResidenceOwnershipEnum residenceOwnershipEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(residenceOwnership)){
			logger.info("Validation error: no option selected from Residence Ownership dropdown");
			residenceOwnershipEnum = null;
			this.setValue(residenceOwnershipEnum);
		} else if(ResidenceOwnershipEnum.valueOf(residenceOwnership) instanceof ResidenceOwnershipEnum){
			logger.info("Residence Ownership dropdown validation passed; " + residenceOwnership + " is a valid residence ownership");
			this.setValue(ResidenceOwnershipEnum.valueOf(residenceOwnership));
		} else {
			logger.info(residenceOwnership + ": No such residence ownership defined in the code");
		}
		
	}
}
