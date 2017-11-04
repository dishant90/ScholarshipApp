package com.tripleS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tripleS.model.EntityBankDetails;
import com.tripleS.service.EntityBankDetailsService;
import com.tripleS.service.EntityDetailsService;
import com.tripleS.service.NotificationService;
import com.tripleS.service.StudentFileService;

@Controller
@RequestMapping("/studentFile")
public class StudentBankAccountDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(StudentBankAccountDetailsController.class);

	@Autowired
	private StudentFileService studentFileService;

	@Autowired
	private EntityDetailsService entityDetailsService;

	@Autowired
	private EntityBankDetailsService entityBankDetailsService;

	@Autowired
	private NotificationService notifyService;
	
	@Autowired
	private HomeController homeController;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
		dataBinder.registerCustomEditor(Date.class, dateEditor);
	}

	@RequestMapping(value = "/bankAccountDetails", method = RequestMethod.GET)
	public String bankAccountDetails(Model model) {
		String fileNo = (String) model.asMap().get("fileNo");
		logger.info("In the bank account details Get Request...File No is " + fileNo);
		model = getBankAccountDetailsByFileNo(fileNo, model);
		return "bankAccountDetails";
	}

	@RequestMapping(value = "/bankAccountDetails/{fileNo}", method = RequestMethod.GET)
	public String bankAccountDetails(@PathVariable("fileNo") String fileNo, Model model) {
		if (!fileNo.isEmpty()) {
			logger.info("Path Variable... File No is " + fileNo);
			if (studentFileService.existsByFileNo(fileNo)) {
				model = getBankAccountDetailsByFileNo(fileNo, model);
				return "bankAccountDetails";
			} else {
				return homeController.invalidFileNoRedirection(fileNo);
			}
		} else {
			return homeController.invalidFileNoRedirection(fileNo);
		}
	}
	
	@RequestMapping(value = "/bankAccountDetails", params = { "addUpdateBankAccountDetails" })
	public String addBankAccountRow(final String fileNo, @Validated EntityBankDetails entityBankDetails,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			logger.info("Found validation errors on Bank Details page for " + fileNo);
			if (studentFileService.existsByFileNo(fileNo)) {
				model = getBankAccountDetailsByFileNo(fileNo, model);
				return "bankAccountDetails";
			} else {
				return homeController.invalidFileNoRedirection(fileNo);
			}
		} else {
			logger.info("File No is " + fileNo);
			entityBankDetails.setEntityDetails(entityDetailsService.findApplicant(fileNo));
			entityBankDetailsService.save(entityBankDetails);
			redirectAttributes.addFlashAttribute("fileNo", fileNo);
			return "redirect:/studentFile/bankAccountDetails";
		}
	}

	@RequestMapping(value = "/bankAccountDetails", params = { "editEntityBankDetails" })
	public String editBankAccountRow(final String fileNo, EntityBankDetails entityBankDetails,
			RedirectAttributes redirectAttributes, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("editEntityBankDetails"));
		entityBankDetails = entityBankDetailsService.findById(rowId);
		redirectAttributes.addFlashAttribute("fileNo", fileNo);
		redirectAttributes.addFlashAttribute("entityBankDetails", entityBankDetails);
		return "redirect:/studentFile/bankAccountDetails";
	}

	@RequestMapping(value = "/bankAccountDetails", params = { "removeEntityBankDetails" })
	public String removeBankAccountRow(final String fileNo, EntityBankDetails entityBankDetails,
			RedirectAttributes redirectAttributes, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeEntityBankDetails"));
		entityDetailsService.delete(rowId);
		redirectAttributes.addFlashAttribute("fileNo", fileNo);
		return "redirect:/studentFile/bankAccountDetails";
	}

	@RequestMapping(value = "/bankAccountDetails", params = { "continueFromBankDetails" })
	public ModelAndView continueFromBankDetails(final String fileNo, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		if (!fileNo.isEmpty()) {
			logger.info("Existing File No: " + fileNo);
			redirectAttributes.addFlashAttribute("fileNo", fileNo);
			modelAndView.setViewName("redirect:/studentFile/residenceDetails");
		} else {
			modelAndView.setViewName(homeController.invalidFileNoRedirection(fileNo));
		}
		return modelAndView;
	}
	
	private Model getBankAccountDetailsByFileNo(String fileNo, Model model) {
		List<EntityBankDetails> entityBankDetailsList = entityBankDetailsService.findByFileNo(fileNo);
		model.asMap().put("entityBankDetailsList", entityBankDetailsList);
		if (model.asMap().get("entityBankDetails") == null) {
			model.asMap().put("entityBankDetails", new EntityBankDetails());
			model.asMap().put("bankAction", "Add Bank Account Details");
		} else {
			model.asMap().put("entityBankDetails", model.asMap().get("entityBankDetails"));
			model.asMap().put("bankAction", "Update Bank Account Details");
		}
		return model;
	}

}