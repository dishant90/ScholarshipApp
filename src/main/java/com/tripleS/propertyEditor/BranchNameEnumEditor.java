package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tripleS.enums.BranchNameEnum;
import com.tripleS.enums.CourseNameEnum;

public class BranchNameEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(BranchNameEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String branch){
		BranchNameEnum branchNameEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(branch)){
			logger.info("Validation error: no option selected from branch dropdown");
			branchNameEnum = null;
			this.setValue(branchNameEnum);
		} else if(BranchNameEnum.valueOf(branch) instanceof BranchNameEnum){
			logger.info("branch dropdown validation passed; " + branch + " is a valid branch");
			this.setValue(BranchNameEnum.valueOf(branch));
		} else {
			logger.info(branch + ": No such branch defined in the code");
		}
		
	}
}
