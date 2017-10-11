package com.tripleS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tripleS.model.EntityDetails;
import com.tripleS.model.StudentFile;
import com.tripleS.service.EntityDetailsService;
import com.tripleS.service.NotificationService;
import com.tripleS.service.StudentFileService;

@Controller
@RequestMapping("/studentApplication")
public class StudentApplicationService {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentApplicationService.class);
	
	@Autowired
	private StudentFileService studentFileService;
	
	@Autowired
	private EntityDetailsService entityDetailsService;
	
	@Autowired
    private NotificationService notifyService;
	
	@InitBinder
    public void initBinder(WebDataBinder dataBinder) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
		dataBinder.registerCustomEditor(Date.class, dateEditor);
    }
	
	@RequestMapping(value="/basicDetails", method = RequestMethod.GET)
	public ModelAndView newCase(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("studentFile", new StudentFile());
		modelAndView.setViewName("basicDetails");
		return modelAndView;
	}
	
	@RequestMapping(value="/basicDetails/{fileNo}", method = RequestMethod.GET)
	public ModelAndView basicDetails(@PathVariable("fileNo") int fileNo){
		ModelAndView modelAndView = new ModelAndView();
		if(fileNo > 0) {
			logger.info("Path Variable... File No is " + fileNo);
			StudentFile studentFile = studentFileService.findByFileNo(fileNo);
			if(studentFile != null) {
				logger.info("Applicant Name: " + studentFile.getEntityDetails().getFirstName());
				modelAndView.addObject("studentFile", studentFile);
				modelAndView.setViewName("basicDetails");
				return modelAndView;
			} else {
				logger.error("Invalid case number: " + fileNo);
				notifyService.addErrorMessage("Invalid case number: " + fileNo);
				modelAndView.setViewName("fragments/home");
				return modelAndView;
			}
		} else {
			logger.error("Invalid case number: " + fileNo);
			notifyService.addErrorMessage("Invalid case number: " + fileNo);
			modelAndView.setViewName("fragments/home");
			return modelAndView;
		}
	}
	
	@RequestMapping(value="/familyDetails/{fileNo}", method = RequestMethod.GET)
	public ModelAndView familyDetails(@PathVariable("fileNo") int fileNo){
		ModelAndView modelAndView = new ModelAndView();
		if(fileNo > 0) {
			logger.info("Path Variable... File No is " + fileNo);
			StudentFile studentFile = studentFileService.findByFileNo(fileNo);
			if(studentFile != null) {
				logger.info("Applicant Name: " + studentFile.getEntityDetails().getFirstName());
				modelAndView.addObject("studentFile", studentFile);
				modelAndView.setViewName("familyDetails");
				return modelAndView;
			} else {
				logger.error("Invalid case number: " + fileNo);
				notifyService.addErrorMessage("Invalid case number: " + fileNo);
				modelAndView.setViewName("fragments/home");
				return modelAndView;
			}
		} else {
			logger.error("Invalid case number: " + fileNo);
			notifyService.addErrorMessage("Invalid case number: " + fileNo);
			modelAndView.setViewName("fragments/home");
			return modelAndView;
		}
	}
	
	@RequestMapping(value="/familyDetails", method = RequestMethod.GET)
	public String familyDetails(Model model){
		//logger.info("In the family details Get Request...File No is " + ((StudentFile) model.asMap().get("studentFile")).getFileNo());
		
		logger.info("In the family details Get Request...File No is " + model.asMap().get("fileNo"));
		int fileNo = (int) model.asMap().get("fileNo");
		List<EntityDetails> relatives = entityDetailsService.findRelativesByFileNo(fileNo);
		/* if(model.asMap().get("relatives") == null) {
			relatives = entityDetailsService.findRelativesByFileNo(fileNo);
		} else {
			relatives = (List<EntityDetails>) model.asMap().get("relatives");
		}
		
		if(model.asMap().get("entityDetails") != null) {
			EntityDetails entityDetails = (EntityDetails) model.asMap().get("entityDetails");
			if(relatives == null) {
				relatives = new ArrayList<EntityDetails>();
			} else {
				EntityDetails applicant;
				if(relatives.size() == 0) {
					applicant = entityDetailsService.findApplicant(fileNo);
				} else {
					applicant = relatives.get(0).getApplicant();
				}
				entityDetails.setApplicant(applicant);
			}
			relatives.add(entityDetails);
		}*/
		model.asMap().put("relatives", relatives);
		if(model.asMap().get("entityDetails") == null) {
			model.asMap().put("entityDetails", new EntityDetails());
			model.asMap().put("entityAction", "Add Member");
		} else {
			model.asMap().put("entityDetails", model.asMap().get("entityDetails"));
			model.asMap().put("entityAction", "Update Member");
		}
		
		return "familyDetails";
	}
	
	@RequestMapping(value="/bankAccountDetails", method = RequestMethod.GET)
	public String bankAccountDetails(Model model){
		int fileNo = (int) model.asMap().get("fileNo");
		logger.info("In the bank account details Get Request...File No is " + model.asMap().get("fileNo"));
		return "bankAccountDetails";
	}
	
	@RequestMapping(value="/familyDetails", params={"addEntityDetails"})
	public String addRelativeRow(final int fileNo, EntityDetails entityDetails, RedirectAttributes redirectAttributes) {
		logger.info("File No is " + fileNo);
		
		entityDetails.setApplicant(entityDetailsService.findApplicant(fileNo));
		entityDetails.setEmailID("temp@xyz.com");
		entityDetails.setMobileNo("9321609101");
		List<EntityDetails> relatives = entityDetails.getRelatives();
		entityDetails = entityDetailsService.save(entityDetails);
		
		if(relatives != null) {
			if(relatives.size() > 0){
				EntityDetails relative = relatives.get(0);
				relative.setApplicant(entityDetails);
				relative.setEmailID("temp@xyz.com");
				relative.setMobileNo("9321609101");
				entityDetailsService.save(relative);
			}
		}
		
		redirectAttributes.addFlashAttribute("fileNo",fileNo);
		//redirectAttributes.addFlashAttribute("entityDetails", entityDetails);
	    return "redirect:/studentApplication/familyDetails";
	}

	@RequestMapping(value="/familyDetails", params={"addSchoolEmployerDetails"})
	public String addSchoolEmployer(
	        final StudentFile studentFile, final BindingResult bindingResult, 
	        final HttpServletRequest req) {
	    final Integer rowId = Integer.valueOf(req.getParameter("addSchoolEmployerDetails"));
	    studentFile.getEntityDetails().getRelatives().get(rowId.intValue()).getRelatives().add(new EntityDetails());
	    return "familyDetails";
	}
	
	@RequestMapping(value="/familyDetails", params={"editEntityDetails"})
	public String editRelativeRow(
			final int fileNo, EntityDetails entityDetails, RedirectAttributes redirectAttributes, 
	        final HttpServletRequest req) {
	    final Integer rowId = Integer.valueOf(req.getParameter("editEntityDetails"));
	    entityDetails = entityDetailsService.findById(rowId);
	    redirectAttributes.addFlashAttribute("fileNo",fileNo);
		redirectAttributes.addFlashAttribute("entityDetails", entityDetails);
	    return "redirect:/studentApplication/familyDetails";
	}
	
	@RequestMapping(value="/familyDetails", params={"removeEntityDetails"})
	public String removeRelativeRow(
			final int fileNo, EntityDetails entityDetails, RedirectAttributes redirectAttributes, 
	        final HttpServletRequest req) {
	    final Integer rowId = Integer.valueOf(req.getParameter("removeEntityDetails"));
	    entityDetailsService.delete(rowId);
	    redirectAttributes.addFlashAttribute("fileNo",fileNo);
		//redirectAttributes.addFlashAttribute("entityDetails", entityDetails);
	    return "redirect:/studentApplication/familyDetails";
	}
	
	@RequestMapping(value="/fragments/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("fragments/home");
		return modelAndView;
	}
	
    @RequestMapping(value = "/basicDetails", method=RequestMethod.POST, params={"saveContinueBasicDetails"})
    public ModelAndView saveContinueStudentFile(@Valid StudentFile studentFile, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	ModelAndView modelAndView = new ModelAndView();
    	if (bindingResult.hasErrors()) {
        	logger.error("Found validation errors");
        	modelAndView.addObject("studentFile", studentFile);
			modelAndView.setViewName("basicDetails");
		} else {
			logger.info("Found no validation errors");
	    	if(studentFile.getId() > 0) {
	    		logger.info("Existing File ID: " + studentFile.getId());
	    		studentFile = studentFileService.update(studentFile);
	    		logger.info("File ID After Update: " + studentFile.getId());
	    		logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully");
		        notifyService.addInfoMessage(studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully!!");
	    	} else {
		        studentFile = studentFileService.save(studentFile);
		        logger.info("New File No: " + studentFile.getFileNo());
		        logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully");
		        notifyService.addInfoMessage(studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully!!");
	    	}
	        //modelAndView.addObject("studentFile", studentFile);
	        redirectAttributes.addFlashAttribute("fileNo", studentFile.getFileNo());
	        modelAndView.setViewName("redirect:/studentApplication/familyDetails");
		}
        return modelAndView;
    }
    
    @RequestMapping(value = "/basicDetails", method=RequestMethod.POST, params={"saveBasicDetails"})
    public ModelAndView saveStudentFile(@Valid StudentFile studentFile, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	ModelAndView modelAndView = new ModelAndView();
    	if (bindingResult.hasErrors()) {
        	logger.error("Found validation errors");
        	modelAndView.addObject("studentFile", studentFile);
			modelAndView.setViewName("basicDetails");
		} else {
			logger.info("Found no validation errors");
	    	if(studentFile.getId() > 0) {
	    		logger.info("Existing File ID: " + studentFile.getId());
	    		studentFile = studentFileService.update(studentFile);
	    		logger.info("File ID After Update: " + studentFile.getId());
	    		logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully");
		        notifyService.addInfoMessage(studentFile.getEntityDetails().getFirstName() + "'s basic details updated successfully!!");
	    	} else {
		        studentFile = studentFileService.save(studentFile);
		        logger.info("New File No: " + studentFile.getFileNo());
		        logger.info(studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully");
		        notifyService.addInfoMessage(studentFile.getEntityDetails().getFirstName() + "'s basic details saved successfully!!");
	    	}
	        //modelAndView.addObject("studentFile", studentFile);
	    	modelAndView.setViewName("basicDetails");
		}
        return modelAndView;
    }
    
    @RequestMapping(value = "/familyDetails", params={"continueFromFamilyDetails"})
    public ModelAndView continueFromFamilyDetails(final int fileNo, RedirectAttributes redirectAttributes) {
    	ModelAndView modelAndView = new ModelAndView();
    	if(fileNo > 0) {
    		logger.info("Existing File No: " + fileNo);
    		redirectAttributes.addFlashAttribute("fileNo", fileNo);
            modelAndView.setViewName("redirect:/studentApplication/bankAccountDetails");
    	} else {
	        // handle exception
    	}
        return modelAndView;
    }
    
    @RequestMapping(value = "/familyDetails", method=RequestMethod.POST, params={"saveFamilyDetails"})
    public ModelAndView saveFamilyDetails(@Valid StudentFile studentFile, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	ModelAndView modelAndView = new ModelAndView();
    	if (bindingResult.hasErrors()) {
        	logger.error("Found validation errors");
        	modelAndView.addObject("studentFile", studentFile);
		} else {
			logger.info("Found no validation errors");
	    	if(studentFile.getId() > 0) {
	    		logger.info("Existing File ID: " + studentFile.getId());
	    		
	    		for (int i = 0; i < studentFile.getEntityDetails().getRelatives().size(); i++) {
	    			studentFile.getEntityDetails().getRelatives().get(i).setApplicant(studentFile.getEntityDetails());
	    		}
	    		
	    		studentFile = studentFileService.update(studentFile);
	    		logger.info("File ID After Update: " + studentFile.getId());
	    		logger.info(studentFile.getEntityDetails().getFirstName() + "'s family details updated successfully");
		        notifyService.addInfoMessage(studentFile.getEntityDetails().getFirstName() + "'s family details updated successfully!!");
	    	} else {
		        studentFile = studentFileService.save(studentFile);
		        logger.info("New File No: " + studentFile.getFileNo());
		        logger.info(studentFile.getEntityDetails().getFirstName() + "'s family details saved successfully");
		        notifyService.addInfoMessage(studentFile.getEntityDetails().getFirstName() + "'s family details saved successfully!!");
	    	}
		}
    	modelAndView.setViewName("familyDetails");
        return modelAndView;
    }
}