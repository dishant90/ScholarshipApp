package com.tripleS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tripleS.exception.UserAlreadyExistException;
import com.tripleS.exception.UserNotFoundException;
import com.tripleS.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tripleS.enums.AcademicYearEnum;
import com.tripleS.enums.BranchNameEnum;
import com.tripleS.enums.CountryEnum;
import com.tripleS.enums.CourseNameEnum;
import com.tripleS.enums.FileStatusEnum;
import com.tripleS.enums.GenderEnum;
import com.tripleS.enums.ResidenceOwnershipEnum;
import com.tripleS.enums.ResidenceTypeEnum;
import com.tripleS.enums.StateEnum;
import com.tripleS.exception.InvalidFileNumberException;
import com.tripleS.exception.InvalidOldPasswordException;
import com.tripleS.exception.NoFileFoundException;
import com.tripleS.propertyEditor.AcademicYearEnumEditor;
import com.tripleS.propertyEditor.BranchNameEnumEditor;
import com.tripleS.propertyEditor.CountryEnumEditor;
import com.tripleS.propertyEditor.CourseNameEnumEditor;
import com.tripleS.propertyEditor.FileStatusEnumEditor;
import com.tripleS.propertyEditor.GenderEnumEditor;
import com.tripleS.propertyEditor.ResidenceOwnershipEnumEditor;
import com.tripleS.propertyEditor.ResidenceTypeEnumEditor;
import com.tripleS.propertyEditor.StateEnumEditor;
import com.tripleS.service.NotificationService;

@ControllerAdvice
public class CommonControllerAdvice extends ResponseEntityExceptionHandler  {

	private static final Logger logger = LoggerFactory.getLogger(CommonControllerAdvice.class);

	@Autowired
	private NotificationService notifyService;
	
	@Autowired
    private MessageSource messages;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
		dataBinder.registerCustomEditor(Date.class, dateEditor);
		dataBinder.registerCustomEditor(CountryEnum.class, new CountryEnumEditor());
		dataBinder.registerCustomEditor(StateEnum.class, new StateEnumEditor());
		dataBinder.registerCustomEditor(GenderEnum.class, new GenderEnumEditor());
		dataBinder.registerCustomEditor(ResidenceOwnershipEnum.class, new ResidenceOwnershipEnumEditor());
		dataBinder.registerCustomEditor(ResidenceTypeEnum.class, new ResidenceTypeEnumEditor());
		dataBinder.registerCustomEditor(FileStatusEnum.class, new FileStatusEnumEditor());
		dataBinder.registerCustomEditor(CourseNameEnum.class, new CourseNameEnumEditor());
		dataBinder.registerCustomEditor(BranchNameEnum.class, new BranchNameEnumEditor());
		dataBinder.registerCustomEditor(AcademicYearEnum.class, new AcademicYearEnumEditor());
	}

	@ExceptionHandler(InvalidFileNumberException.class)
	public ModelAndView invalidFileNoRedirection(InvalidFileNumberException ifne) {
		logger.error("Invalid case number - Error Code: " + ifne.getErrCode());
		logger.error("Invalid case number - Error Message: " + ifne.getErrMsg());
		notifyService.addErrorMessage(ifne.getErrMsg());
		ModelAndView modelAndView = new ModelAndView("fragments/home");
		return modelAndView;
	}
	
	// 406
	@ExceptionHandler(InvalidOldPasswordException.class)
	public ResponseEntity<Object> InvalidOldPasswordRedirection(final RuntimeException ex, final WebRequest request) {
		logger.error("Invalid old password exception");
		logger.error("406 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.invalidOldPasswordMessage", null, request.getLocale()), "InvalidOldPassword");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	@ExceptionHandler(NoFileFoundException.class)
	public void noFileFoundExceptionHandler(NoFileFoundException nffe) {
		logger.error("Error Code: " + nffe.getErrCode());
		logger.error("Error Message: " + nffe.getErrMsg());
		notifyService.addErrorMessage(nffe.getErrMsg());
	}

	 // 404
    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
        logger.error("404 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.userNotFound", null, request.getLocale()), "UserNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409
    @ExceptionHandler({ UserAlreadyExistException.class })
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        logger.error("409 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.userAlreadyExist", null, request.getLocale()), "UserAlreadyExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 500
    @ExceptionHandler({ MailAuthenticationException.class })
    public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.email.config.error", null, request.getLocale()), "MailError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
