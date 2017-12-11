package com.tripleS.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tripleS.exception.InvalidFileNumberException;
import com.tripleS.model.ResidenceDetails;
import com.tripleS.service.EntityDetailsService;
import com.tripleS.service.NotificationService;
import com.tripleS.service.ResidenceDetailsService;
import com.tripleS.service.StudentFileService;

@Controller
@RequestMapping("/studentFile")
public class StudentResidenceDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(StudentResidenceDetailsController.class);

	@Autowired
	private StudentFileService studentFileService;

	@Autowired
	private EntityDetailsService entityDetailsService;

	@Autowired
	private ResidenceDetailsService residenceDetailsService;

	@Autowired
	private NotificationService notifyService;
	
	@RequestMapping(value = "/residenceDetails", method = RequestMethod.GET)
	public String residenceDetails(Model model) {
		String fileNo = (String) model.asMap().get("fileNo");
		logger.info("In the residence details Get Request...File No is " + fileNo);
		model = getResidenceDetailsByFileNo(fileNo, model);
		return "residenceDetails";
	}

	@RequestMapping(value = "/residenceDetails/{fileNo}", method = RequestMethod.GET)
	public String residenceDetails(@PathVariable("fileNo") String fileNo, Model model) {
		if (!fileNo.isEmpty()) {
			logger.info("Path Variable... File No is " + fileNo);
			if (studentFileService.existsByFileNo(fileNo)) {
				model = getResidenceDetailsByFileNo(fileNo, model);
				return "residenceDetails";
			} else {
				throw new InvalidFileNumberException(fileNo);
			}
		} else {
			throw new InvalidFileNumberException(fileNo);
		}
	}

	@RequestMapping(value = "/residenceDetails", method = RequestMethod.POST, params = { "saveContinueResidenceDetails" })
	public ModelAndView saveResidenceDetailsAndContinue(@Valid ResidenceDetails residenceDetails, BindingResult bindingResult,
			RedirectAttributes redirectAttributes,final String fileNo) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			logger.error("Found validation errors");
			modelAndView.addObject("residenceDetails", residenceDetails);
			modelAndView.addObject("fileNo", fileNo);
			modelAndView.setViewName("residenceDetails");
		} else {
			logger.info("Found no validation errors");
			if (residenceDetails.getId() > 0) {
				logger.info("Existing Residence ID: " + residenceDetails.getId());
				residenceDetails.setEntityDetails(entityDetailsService.findApplicant(fileNo));
				residenceDetails = residenceDetailsService.save(residenceDetails);
				logger.info("Residence Details After Update: " + residenceDetails.toString());
				logger.info("Residence details updated successfully");
				notifyService.addInfoMessage("Residence details updated successfully");
			} else {
				residenceDetails.setEntityDetails(entityDetailsService.findApplicant(fileNo));
				residenceDetails = residenceDetailsService.save(residenceDetails);
				logger.info("New record created in ResidenceDetails: " + residenceDetails.toString());
				logger.info("Residence details saved successfully");
				notifyService.addInfoMessage("Residence details saved successfully");
			}
			redirectAttributes.addFlashAttribute("fileNo", fileNo);
			modelAndView.setViewName("redirect:/studentFile/curriculumRecord");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/residenceDetails", method = RequestMethod.POST, params = { "saveResidenceDetails" })
	public ModelAndView saveResidenceDetails(@Valid ResidenceDetails residenceDetails, BindingResult bindingResult,
			RedirectAttributes redirectAttributes,final String fileNo) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			logger.error("Found validation errors");
			modelAndView.addObject("residenceDetails", residenceDetails);
		} else {
			logger.info("Found no validation errors");
			if (residenceDetails.getId() > 0) {
				logger.info("Existing Residence ID: " + residenceDetails.getId());
				residenceDetails.setEntityDetails(entityDetailsService.findApplicant(fileNo));
				residenceDetails = residenceDetailsService.save(residenceDetails);
				logger.info("Residence Details After Update: " + residenceDetails.toString());
				logger.info("Residence details updated successfully");
				notifyService.addInfoMessage("Residence details updated successfully");
			} else {
				residenceDetails.setEntityDetails(entityDetailsService.findApplicant(fileNo));
				residenceDetails = residenceDetailsService.save(residenceDetails);
				logger.info("New record created in ResidenceDetails: " + residenceDetails.toString());
				logger.info("Residence details saved successfully");
				notifyService.addInfoMessage("Residence details saved successfully");
			}
		}
		modelAndView.addObject("fileNo", fileNo);
		modelAndView.setViewName("residenceDetails");
		return modelAndView;
	}

	private Model getResidenceDetailsByFileNo(String fileNo, Model model) {
		ResidenceDetails residenceDetails = residenceDetailsService.findByFileNo(fileNo);
		if(residenceDetails == null){
			residenceDetails = new ResidenceDetails();
		}
		model.asMap().put("residenceDetails", residenceDetails);
		return model;
	}

}