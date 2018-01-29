package com.tripleS.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tripleS.enums.FileStatusEnum;
import com.tripleS.exception.InvalidFileNumberException;
import com.tripleS.model.ResidenceDetails;
import com.tripleS.model.StudentFile;
import com.tripleS.service.EntityBankDetailsService;
import com.tripleS.service.EntityDetailsService;
import com.tripleS.service.NotificationService;
import com.tripleS.service.ResidenceDetailsService;
import com.tripleS.service.StudentCurriculumRecordService;
import com.tripleS.service.StudentFileService;
import com.tripleS.validation.groups.StudentValidations;

@Controller
@RequestMapping("/studentFile")
public class ValidateStudentFileController {

	private static final Logger logger = LoggerFactory.getLogger(ValidateStudentFileController.class);

	@Autowired
    private MessageSource messages;
	
	@Autowired
	private StudentFileService studentFileService;
	
	@Autowired
	private EntityDetailsService entityDetailsService;
	
	@Autowired
	private EntityBankDetailsService entityBankDetailsService;
	
	@Autowired
	private ResidenceDetailsService residenceDetailsService;
	
	@Autowired
	private StudentCurriculumRecordService studentCurriculumRecordService;

	@Autowired
	private NotificationService notifyService;

	@RequestMapping(value = "/validateFile/{fileNo}", method = RequestMethod.GET)
	public String validateFile(@PathVariable("fileNo") String fileNo, Model model, final Locale locale) {
		try {
			if (!fileNo.isEmpty()) {
				logger.info("Path Variable... File No is " + fileNo);
				StudentFile studentFile = studentFileService.findByFileNo(fileNo);
				if (studentFile != null) {
					boolean validFile = false;
					boolean fileSubmitted = false;
					if(FileStatusEnum.NEW.equals(studentFile.getFileStatus())) {
						List<String> requiredDetails = new ArrayList<String>();
						if(studentFile.getEntityDetails() == null) {
							requiredDetails.add(messages.getMessage("basicDetails.header", null,locale));
							logger.info("Basic details are not submitted");
						}
						if(entityDetailsService.findRelativesByFileNo(fileNo).isEmpty()) {
							requiredDetails.add(messages.getMessage("familyDetails.header", null,locale));
							logger.info("Family details are not submitted");
						}
						if(entityBankDetailsService.findByFileNo(fileNo).isEmpty()) {
							requiredDetails.add(messages.getMessage("bankAccountDetails.header", null,locale));
							logger.info("Bank account details are not submitted");
						}
						if(residenceDetailsService.findByFileNo(fileNo) == null) {
							requiredDetails.add(messages.getMessage("residenceDetails.header", null,locale));
							logger.info("Residence details are not submitted");
						}
						if(studentCurriculumRecordService.findByFileNo(fileNo) == null || 
								studentCurriculumRecordService.findByFileNo(fileNo).size() == 0 ) {
							requiredDetails.add(messages.getMessage("curriculumRecord.header", null,locale));
							logger.info("Curriculum record is not submitted");
						}
						if(requiredDetails.isEmpty()){
							validFile = true;
							logger.info("All mandatory details are provided");
						} else {
							model.asMap().put("requiredDetails", requiredDetails);
							logger.info("Following screens are mandatory before you submit the case:"
									+ requiredDetails.toString());
						}
						
					} else if(FileStatusEnum.SUBMITTED.equals(studentFile.getFileStatus())) {
						validFile = true;
						fileSubmitted = true;
						logger.info(messages.getMessage("validateFile.fileAlreadySubmitted.label", null, locale));
					}
					model.asMap().put("validFile", validFile);
					model.asMap().put("fileSubmitted", fileSubmitted);
					return "validateFile";
				} else {
					throw new InvalidFileNumberException(fileNo);
				}
			} else {
				throw new InvalidFileNumberException(fileNo);
			}
		} catch(Exception ex){
			logger.error("Exception at /validateFile/{fileNo} " + ex.getMessage());
			return "validateFile";
		}
	}
	
	@RequestMapping(value = "/validateFile", method = RequestMethod.POST, params = { "submitFile" })
	public ModelAndView submitStudentFile(final Locale locale,
			RedirectAttributes redirectAttributes,final String fileNo) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("Submitting the validated file..." + fileNo);
		StudentFile studentFile = studentFileService.findByFileNo(fileNo);
		studentFile.setFileStatus(FileStatusEnum.SUBMITTED);
		studentFileService.update(studentFile);
		notifyService.addInfoMessage(messages.getMessage("message.caseSubmitSuccess", null, locale));
		modelAndView.setViewName("fragments/home");
		return modelAndView;
	}
	
}