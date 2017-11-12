package com.tripleS.propertyEditor;

import java.beans.PropertyEditorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.StateEnum;

public class StateEnumEditor extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(StateEnumEditor.class);
	
	private static final String DEFAULT_DROPDOWN_VALUE = "NONE";
	
	@Override
	public void setAsText(String state){
		StateEnum stateEnum;
		if(DEFAULT_DROPDOWN_VALUE.equals(state)){
			logger.info("Validation error: no option selected from State dropdown");
			stateEnum = null;
			this.setValue(stateEnum);
		} else if(StateEnum.valueOf(state) instanceof StateEnum){
			logger.info("State dropdown validation passed; " + state + " is a valid state");
			this.setValue(StateEnum.valueOf(state));
		} else {
			logger.info(state + ": No such state defined in the code");
		}
		
	}
}
