package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.CourseNameEnum;

public class CourseNameEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(CourseNameEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String courseName){
		CourseNameEnum courseNameEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(courseName)){
			logger.info("Validation error: no option selected from course dropdown");
			courseNameEnum = null;
			this.setValue(courseNameEnum);
		} else if(CourseNameEnum.valueOf(courseName) instanceof CourseNameEnum){
			logger.info("Course dropdown validation passed; " + courseName + " is a valid course");
			this.setValue(CourseNameEnum.valueOf(courseName));
		} else {
			logger.info(courseName + ": No such course defined in the code");
		}
		
	}
}
