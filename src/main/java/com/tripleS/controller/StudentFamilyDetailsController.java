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
@RequestMapping("/studentFile")
public class StudentFamilyDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(StudentFamilyDetailsController.class);

	@Autowired
	private StudentFileService studentFileService;

	@Autowired
	private EntityDetailsService entityDetailsService;

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

	@RequestMapping(value = "/familyDetails", method = RequestMethod.GET)
	public String familyDetails(Model model) {
		String fileNo = (String) model.asMap().get("fileNo");
		logger.info("In the family details Get Request...File No is " + fileNo);
		model = getFamilyDetailsByFileNo(fileNo, model);
		return "familyDetails";
	}

	@RequestMapping(value = "/familyDetails/{fileNo}", method = RequestMethod.GET)
	public String familyDetails(@PathVariable("fileNo") String fileNo, Model model) {
		if (!fileNo.isEmpty()) {
			logger.info("Path Variable... File No is " + fileNo);
			if (studentFileService.existsByFileNo(fileNo)) {
				model = getFamilyDetailsByFileNo(fileNo, model);
				return "familyDetails";
			} else {
				return homeController.invalidFileNoRedirection(fileNo);
			}
		} else {
			return homeController.invalidFileNoRedirection(fileNo);
		}
	}

	@RequestMapping(value = "/familyDetails", params = { "addUpdateEntityDetails" })
	public String addRelativeRow(final String fileNo, EntityDetails entityDetails,
			RedirectAttributes redirectAttributes) {
		logger.info("File No is " + fileNo);

		entityDetails.setApplicant(entityDetailsService.findApplicant(fileNo));
		entityDetails.setEmailID("temp@xyz.com");
		entityDetails.setMobileNo("9321609101");
		List<EntityDetails> relatives = entityDetails.getRelatives();
		entityDetails = entityDetailsService.save(entityDetails);

		if (relatives != null) {
			if (relatives.size() > 0) {
				EntityDetails relative = relatives.get(0);
				relative.setApplicant(entityDetails);
				relative.setEmailID("temp@xyz.com");
				relative.setMobileNo("9321609101");
				entityDetailsService.save(relative);
			}
		}

		redirectAttributes.addFlashAttribute("fileNo", fileNo);
		return "redirect:/studentFile/familyDetails";
	}

	@RequestMapping(value = "/familyDetails", params = { "addSchoolEmployerDetails" })
	public String addSchoolEmployer(final StudentFile studentFile, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("addSchoolEmployerDetails"));
		studentFile.getEntityDetails().getRelatives().get(rowId.intValue()).getRelatives().add(new EntityDetails());
		return "familyDetails";
	}

	@RequestMapping(value = "/familyDetails", params = { "editEntityDetails" })
	public String editRelativeRow(final String fileNo, EntityDetails entityDetails,
			RedirectAttributes redirectAttributes, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("editEntityDetails"));
		entityDetails = entityDetailsService.findById(rowId);
		redirectAttributes.addFlashAttribute("fileNo", fileNo);
		redirectAttributes.addFlashAttribute("entityDetails", entityDetails);
		return "redirect:/studentFile/familyDetails";
	}

	@RequestMapping(value = "/familyDetails", params = { "removeEntityDetails" })
	public String removeRelativeRow(final String fileNo, EntityDetails entityDetails,
			RedirectAttributes redirectAttributes, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeEntityDetails"));
		entityDetailsService.delete(rowId);
		redirectAttributes.addFlashAttribute("fileNo", fileNo);
		return "redirect:/studentFile/familyDetails";
	}

	@RequestMapping(value = "/familyDetails", params = { "continueFromFamilyDetails" })
	public ModelAndView continueFromFamilyDetails(final String fileNo, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		if (!fileNo.isEmpty()) {
			logger.info("Existing File No: " + fileNo);
			redirectAttributes.addFlashAttribute("fileNo", fileNo);
			modelAndView.setViewName("redirect:/studentFile/bankAccountDetails");
		} else {
			modelAndView.setViewName(homeController.invalidFileNoRedirection(fileNo));
		}
		return modelAndView;
	}

	private Model getFamilyDetailsByFileNo(String fileNo, Model model) {
		List<EntityDetails> relatives = entityDetailsService.findRelativesByFileNo(fileNo);
		model.asMap().put("relatives", relatives);
		if (model.asMap().get("entityDetails") == null) {
			model.asMap().put("entityDetails", new EntityDetails());
			model.asMap().put("entityAction", "Add Member");
		} else {
			model.asMap().put("entityDetails", model.asMap().get("entityDetails"));
			model.asMap().put("entityAction", "Update Member");
		}
		return model;
	}

}