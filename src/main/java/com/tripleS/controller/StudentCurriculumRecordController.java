package com.tripleS.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import com.tripleS.model.EntityBankDetails;
import com.tripleS.model.StudentCurriculumRecord;
import com.tripleS.service.EntityBankDetailsService;
import com.tripleS.service.EntityDetailsService;
import com.tripleS.service.NotificationService;
import com.tripleS.service.StudentCurriculumRecordService;
import com.tripleS.service.StudentFileService;

@Controller
@RequestMapping("/studentFile")
public class StudentCurriculumRecordController {

	private static final Logger logger = LoggerFactory.getLogger(StudentCurriculumRecordController.class);

	@Autowired
	private StudentCurriculumRecordService studentCurriculumRecordService;
	
	@Autowired
	private StudentFileService studentFileService;

	@Autowired
	private EntityDetailsService entityDetailsService;

	@Autowired
	private NotificationService notifyService;
	
	@RequestMapping(value = "/curriculumRecord", method = RequestMethod.GET)
	public String curriculumRecord(Model model) {
		String fileNo = (String) model.asMap().get("fileNo");
		logger.info("In the curriculum record Get Request...File No is " + fileNo);
		model = getCurriculumRecordByFileNo(fileNo, model);
		return "curriculumRecord";
	}

	@RequestMapping(value = "/curriculumRecord/{fileNo}", method = RequestMethod.GET)
	public String curriculumRecord(@PathVariable("fileNo") String fileNo, Model model) {
		if (!fileNo.isEmpty()) {
			logger.info("Path Variable... File No is " + fileNo);
			if (studentFileService.existsByFileNo(fileNo)) {
				model = getCurriculumRecordByFileNo(fileNo, model);
				return "curriculumRecord";
			} else {
				throw new InvalidFileNumberException(fileNo);
			}
		} else {
			throw new InvalidFileNumberException(fileNo);
		}
	}
	
	@RequestMapping(value = "/curriculumRecord", params = { "addUpdateCurriculumRecord" })
	public String addCurriculumRecord(final String fileNo, @Validated StudentCurriculumRecord studentCurriculumRecord,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			logger.info("Found validation errors on Curriculum Record page for " + fileNo);
			if (studentFileService.existsByFileNo(fileNo)) {
				model = getCurriculumRecordByFileNo(fileNo, model);
				return "curriculumRecord";
			} else {
				throw new InvalidFileNumberException(fileNo);
			}
		} else {
			logger.info("File No is " + fileNo);
			studentCurriculumRecord.getStudentCourseDetails().setEntityDetails(entityDetailsService.findApplicant(fileNo));
			studentCurriculumRecordService.save(studentCurriculumRecord);
			redirectAttributes.addFlashAttribute("fileNo", fileNo);
			return "redirect:/studentFile/curriculumRecord";
		}
	}
	
	@RequestMapping(value = "/curriculumRecord", params = { "onChangeOfCourse" })
	public String onChangeofCourse(final String fileNo, StudentCurriculumRecord studentCurriculumRecord,
			RedirectAttributes redirectAttributes, Model model) {
		if (studentFileService.existsByFileNo(fileNo)) {
			List<StudentCurriculumRecord> studentCurriculumRecordList = getCurriculumRecordByFileNo(fileNo);
			for(int i=0; i<studentCurriculumRecordList.size();i++){
				if(studentCurriculumRecordList.get(i).getStudentCourseDetails().getCourseName().equals(
						studentCurriculumRecord.getStudentCourseDetails().getCourseName())){
					studentCurriculumRecord.setStudentCourseDetails(
							studentCurriculumRecordList.get(i).getStudentCourseDetails());
				}
			}
			model.asMap().put("studentCurriculumRecord", studentCurriculumRecord);
			model.asMap().put("studentCurriculumRecordList", studentCurriculumRecordList);
			if(model.asMap().get("fileNo") == null) {
				model.asMap().put("fileNo", fileNo);
			}
			/*if(model.asMap().get("studentCurriculumRecordAction") != null){
				if(model.asMap().get("studentCurriculumRecordAction").equals("Add New Record")){
					model.asMap().put("studentCurriculumRecordAction", "Add New Record");
				} else {
					model.asMap().put("studentCurriculumRecordAction", "Update Record");
				}
			}*/
			return "curriculumRecord";
		} else {
			throw new InvalidFileNumberException(fileNo);
		}
		
	}

	@RequestMapping(value = "/curriculumRecord", params = { "editCurriculumRecord" })
	public String editCurriculumRecord(final String fileNo, StudentCurriculumRecord studentCurriculumRecord,
			RedirectAttributes redirectAttributes, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("editCurriculumRecord"));
		studentCurriculumRecord = studentCurriculumRecordService.findById(rowId);
		redirectAttributes.addFlashAttribute("fileNo", fileNo);
		redirectAttributes.addFlashAttribute("studentCurriculumRecord", studentCurriculumRecord);
		return "redirect:/studentFile/curriculumRecord";
	}

	@RequestMapping(value = "/curriculumRecord", params = { "removeCurriculumRecord" })
	public String removecurriculumRecord(final String fileNo, StudentCurriculumRecord studentCurriculumRecord,
			RedirectAttributes redirectAttributes, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeCurriculumRecord"));
		studentCurriculumRecordService.delete(rowId);
		redirectAttributes.addFlashAttribute("fileNo", fileNo);
		return "redirect:/studentFile/curriculumRecord";
	}

	@RequestMapping(value = "/curriculumRecord", params = { "continueFromCurriculumRecord" })
	public ModelAndView continueFromCurriculumRecord(final String fileNo, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		if (!fileNo.isEmpty()) {
			logger.info("Existing File No: " + fileNo);
			redirectAttributes.addFlashAttribute("fileNo", fileNo);
			modelAndView.setViewName("redirect:/studentFile/studentReferences");
		} else {
			throw new InvalidFileNumberException(fileNo);
		}
		return modelAndView;
	}
	
	private Model getCurriculumRecordByFileNo(String fileNo, Model model) {
		List<StudentCurriculumRecord> studentCurriculumRecordList = getCurriculumRecordByFileNo(fileNo);
		model.asMap().put("studentCurriculumRecordList", studentCurriculumRecordList);
		if (model.asMap().get("studentCurriculumRecord") == null) {
			model.asMap().put("studentCurriculumRecord", new StudentCurriculumRecord());
			//model.asMap().put("studentCurriculumRecordAction", "Add New Record");
		} else {
			model.asMap().put("studentCurriculumRecord", model.asMap().get("studentCurriculumRecord"));
			//model.asMap().put("studentCurriculumRecordAction", "Update Record");
		}
		if(model.asMap().get("fileNo") == null) {
			model.asMap().put("fileNo", fileNo);
		}
		return model;
	}
	
	private List<StudentCurriculumRecord> getCurriculumRecordByFileNo(String fileNo) {
		return studentCurriculumRecordService.findByFileNo(fileNo);
	}

}