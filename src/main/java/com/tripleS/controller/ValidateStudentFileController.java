package com.tripleS.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.tripleS.exception.InvalidFileNumberException;
import com.tripleS.model.ResidenceDetails;
import com.tripleS.model.StudentFile;
import com.tripleS.service.EntityBankDetailsService;
import com.tripleS.service.EntityDetailsService;
import com.tripleS.service.NotificationService;
import com.tripleS.service.ResidenceDetailsService;
import com.tripleS.service.StudentFileService;
import com.tripleS.validation.groups.StudentValidations;

@Controller
@RequestMapping("/studentFile")
public class ValidateStudentFileController {

	private static final Logger logger = LoggerFactory.getLogger(ValidateStudentFileController.class);

	@Autowired
	private StudentFileService studentFileService;
	
	@Autowired
	private EntityDetailsService entityDetailsService;
	
	@Autowired
	private EntityBankDetailsService entityBankDetailsService;
	
	@Autowired
	private ResidenceDetailsService residenceDetailsService;

	@Autowired
	private NotificationService notifyService;

	@RequestMapping(value = "/validateFile/{fileNo}", method = RequestMethod.GET)
	public String validateFile(@PathVariable("fileNo") String fileNo, Model model) {
		try {
			if (!fileNo.isEmpty()) {
				logger.info("Path Variable... File No is " + fileNo);
				StudentFile studentFile = studentFileService.findByFileNo(fileNo);
				if (studentFile != null) {
					boolean validFile = false;
					List<String> requiredDetails = new ArrayList<String>();
					if(studentFile.getEntityDetails() == null) {
						requiredDetails.add("Basic Details");
					}
					if(entityDetailsService.findRelativesByFileNo(fileNo).isEmpty()) {
						requiredDetails.add("Family Details");
					}
					if(entityBankDetailsService.findByFileNo(fileNo).isEmpty()) {
						requiredDetails.add("Bank Account Details");
					}
					if(residenceDetailsService.findByFileNo(fileNo) == null) {
						requiredDetails.add("Residence Details");
					}
					if(requiredDetails.isEmpty()){
						validFile = true;
						logger.info("All mandatory details are provided");
					} else {
						model.asMap().put("requiredDetails", requiredDetails);
						logger.info("Following screens are mandatory before you submit the case:"
								+ requiredDetails.toString());
					}
					model.asMap().put("validFile", validFile);
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
	public ModelAndView submitStudentFile(
			RedirectAttributes redirectAttributes,final String fileNo) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("Submitting the validated file..." + fileNo);
		modelAndView.addObject("fileNo", fileNo);
		modelAndView.setViewName("validateFile");
		return modelAndView;
	}
	
}