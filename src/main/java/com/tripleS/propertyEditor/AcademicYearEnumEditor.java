package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.AcademicYearEnum;

public class AcademicYearEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(AcademicYearEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String academicYear){
		AcademicYearEnum academicYearEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(academicYear)){
			logger.info("Validation error: no option selected from academic year dropdown");
			academicYearEnum = null;
			this.setValue(academicYearEnum);
		} else if(AcademicYearEnum.valueOf(academicYear) instanceof AcademicYearEnum){
			logger.info("Academic year dropdown validation passed; " + academicYear + " is a valid academic year");
			this.setValue(AcademicYearEnum.valueOf(academicYear));
		} else {
			logger.info(academicYear + ": No such academic year defined in the code");
		}
		
	}
}
