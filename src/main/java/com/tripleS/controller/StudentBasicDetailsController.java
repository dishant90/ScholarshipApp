package com.tripleS.controller;

import javax.validation.Valid;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tripleS.exception.InvalidFileNumberException;
import com.tripleS.model.StudentFile;
import com.tripleS.service.NotificationService;
import com.tripleS.service.StudentFileService;
import com.tripleS.validation.groups.StudentValidations;

@Controller
@RequestMapping("/studentFile")
public class StudentBasicDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(StudentBasicDetailsController.class);

	@Autowired
	private StudentFileService studentFileService;

	@Autowired
	private NotificationService notifyService;
	
	@RequestMapping(value = "/basicDetails", method = RequestMethod.GET)
	public ModelAndView newCase() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("studentFile", new StudentFile());
		modelAndView.setViewName("basicDetails");
		return modelAndView;
	}

	@RequestMapping(value = "/basicDetails/{fileNo}", method = RequestMethod.GET)
	public String basicDetails(@PathVariable("fileNo") String fileNo, Model model) {
		if (!fileNo.isEmpty()) {
			logger.info("Path Variable... File No is " + fileNo);
			StudentFile studentFile = studentFileService.findByFileNo(fileNo);
			if (studentFile != null) {
				logger.info("Applicant Name: " + studentFile.getEntityDetails().getFirstName());
				model.asMap().put("studentFile", studentFile);
				return "basicDetails";
			} else {
				throw new InvalidFileNumberException("S001", fileNo);
			}
		} else {
			throw new InvalidFileNumberException("S001", fileNo);
		}
	}

	@RequestMapping(value = "/basicDetails", method = RequestMethod.POST, params = { "saveContinueBasicDetails" })
	public ModelAndView saveBasicDetailsAndContinue(@Validated({StudentValidations.class}) StudentFile studentFile, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			logger.error("Found validation errors while saving student's basic details");
			modelAndView.addObject("studentFile", studentFile);
			modelAndView.setViewName("basicDetails");
		} else {
			logger.info("Found no validation errors");
			if (studentFile.getId() > 0) {
				logger.info("Existing File ID: " + studentFile.getId());
				studentFile = studentFileService.update(studentFile);
				logger.info("File ID After Update: " + studentFile.getId());
				logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully");
				notifyService.addInfoMessage(
						studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully!!");
			} else {
				studentFile = studentFileService.save(studentFile);
				logger.info("New File No: " + studentFile.getFileNo());
				logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully");
				notifyService.addInfoMessage(
						studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully!!");
			}
			redirectAttributes.addFlashAttribute("fileNo", studentFile.getFileNo());
			modelAndView.setViewName("redirect:/studentFile/familyDetails");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/basicDetails", method = RequestMethod.POST, params = { "saveBasicDetails" })
	public ModelAndView saveBasicDetails(@Validated({StudentValidations.class}) StudentFile studentFile, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			logger.error("Found validation errors while saving student's basic details");
			modelAndView.addObject("studentFile", studentFile);
		} else {
			logger.info("Found no validation errors");
			if (studentFile.getId() > 0) {
				logger.info("Existing File ID: " + studentFile.getId());
				studentFile = studentFileService.update(studentFile);
				logger.info("File ID After Update: " + studentFile.getId());
				logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully");
				notifyService.addInfoMessage(
						studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully!!");
			} else {
				studentFile = studentFileService.save(studentFile);
				logger.info("New File No: " + studentFile.getFileNo());
				logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully");
				notifyService.addInfoMessage(
						studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully!!");
			}
			modelAndView.addObject("fileNo", studentFile.getFileNo());
		}
		modelAndView.setViewName("basicDetails");
		return modelAndView;
	}
	
}