package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.FileStatusEnum;

public class FileStatusEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(FileStatusEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	private static final String EMPTY_STRING = "";
	
	@Override
	public void setAsText(String fileStatus){
		FileStatusEnum fileStatusEnum;
		if(EMPTY_STRING.equals(fileStatus)){
			logger.info("As file status attribute has empty string, should be a new case");
			this.setValue(FileStatusEnum.NEW);
		} else if(DEFAULT_DROPDOWN_VALUE.equals(fileStatus)){
			logger.info("Validation error: no option selected from File status dropdown");
			fileStatusEnum = null;
			this.setValue(fileStatusEnum);
		} else if(FileStatusEnum.valueOf(fileStatus) instanceof FileStatusEnum){
			logger.info("File status dropdown validation passed; " + fileStatus + " is a valid file status");
			this.setValue(FileStatusEnum.valueOf(fileStatus));
		} else {
			logger.info(fileStatus + ": No such file status defined in the code");
		}
	}
}
