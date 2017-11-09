package com.tripleS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.tripleS.enums.CountryEnum;
import com.tripleS.exception.InvalidFileNumberException;
import com.tripleS.propertyEditor.CountryEnumEditor;
import com.tripleS.service.NotificationService;

@ControllerAdvice
public class CommonControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonControllerAdvice.class);
	
	@Autowired
	private NotificationService notifyService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
		dataBinder.registerCustomEditor(Date.class, dateEditor);
		dataBinder.registerCustomEditor(CountryEnum.class, new CountryEnumEditor());
	}
	
	@ExceptionHandler(InvalidFileNumberException.class)
	public ModelAndView invalidFileNoRedirection(InvalidFileNumberException ifne) {
		logger.error("Invalid case number - Error Code: " + ifne.getErrCode());
		logger.error("Invalid case number - Error Message: " + ifne.getErrMsg());
		notifyService.addErrorMessage("Invalid case number: " + ifne.getErrMsg());
		ModelAndView modelAndView = new ModelAndView("fragments/home");
		return modelAndView;
	}
	
}
