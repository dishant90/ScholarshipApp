package com.tripleS.controller;

import java.util.List;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tripleS.exception.InvalidFileNumberException;
import com.tripleS.exception.NoFileFoundException;
import com.tripleS.model.SearchStudentFile;
import com.tripleS.model.StudentFile;
import com.tripleS.service.NotificationService;
import com.tripleS.service.SearchStudentFileService;
import com.tripleS.service.StudentFileService;
import com.tripleS.validation.groups.SearchStudentFileValidations;
import com.tripleS.validation.groups.StudentValidations;

@Controller
@RequestMapping("/searchStudentFile")
public class SearchStudentFileController {

	private static final Logger logger = LoggerFactory.getLogger(SearchStudentFileController.class);

	@Autowired
	private StudentFileService studentFileService;

	@Autowired
	private SearchStudentFileService searchStudentFileService;

	@Autowired
	private NotificationService notifyService;

	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	public ModelAndView basicFileSearch() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("searchStudentFile", new SearchStudentFile());
		modelAndView.setViewName("searchStudentFile");
		return modelAndView;
	}

	@RequestMapping(value = "/basicWithResults", method = RequestMethod.GET)
	public ModelAndView basicFileSearchWithResults(
			@ModelAttribute("searchStudentFile") SearchStudentFile searchStudentFile,
			@ModelAttribute("studentFilesFound") List<StudentFile> studentFilesFound, ModelAndView modelAndView) {
		notifyService.addInfoMessage(studentFilesFound.size() + " case(s) found!!");
		modelAndView.addObject("searchStudentFile", searchStudentFile);
		modelAndView.addObject("studentFilesFound", studentFilesFound);
		modelAndView.setViewName("searchStudentFile");
		return modelAndView;
	}

	@RequestMapping(value = "basicDetailsByFileNo", method = RequestMethod.GET)
	public String searchBasicDetailsByFileNo(@RequestParam(name = "searchFileNo", required = true) String fileNo,
			Model model) {
		if (!fileNo.isEmpty()) {
			logger.info("Request parameter... File No is " + fileNo);
			StudentFile studentFile = studentFileService.findByFileNo(fileNo);
			if (studentFile != null) {
				logger.info("Applicant Name: " + studentFile.getEntityDetails().getFirstName());
				model.asMap().put("studentFile", studentFile);
				model.asMap().put("fileNo", studentFile.getFileNo());
				return "basicDetails";
			} else {
				throw new InvalidFileNumberException(fileNo);
			}
		} else {
			throw new InvalidFileNumberException(fileNo);
		}
	}

	@RequestMapping(value = "/basic", method = RequestMethod.POST)
	public ModelAndView basicFileSearch(
			@Validated({ SearchStudentFileValidations.class }) SearchStudentFile searchStudentFile,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		List<StudentFile> studentFilesFound = null;
		if (bindingResult.hasErrors()) {
			logger.error("Found validation errors while searching for student cases");
			modelAndView.addObject("searchStudentFile", searchStudentFile);
			modelAndView.setViewName("searchStudentFile");
		} else {
			logger.info("Found no validation errors");
			if (searchStudentFile != null) {
				studentFilesFound = searchStudentFileService.search(searchStudentFile);
				if (studentFilesFound != null && studentFilesFound.size() > 0) {
					redirectAttributes.addFlashAttribute("searchStudentFile", searchStudentFile);
					redirectAttributes.addFlashAttribute("studentFilesFound", studentFilesFound);
					modelAndView.setViewName("redirect:/searchStudentFile/basicWithResults");
				} else {
					notifyService.addErrorMessage("No case found!! Please verify the filter criteria.");
					modelAndView.addObject("searchStudentFile", searchStudentFile);
					modelAndView.setViewName("searchStudentFile");
				}
			}
		}

		return modelAndView;
	}

}