package com.tripleS.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tripleS.model.EntityDetails;
import com.tripleS.model.StudentCourseDetails;
import com.tripleS.model.StudentCurriculumRecord;
import com.tripleS.repository.StudentCourseDetailsRepository;
import com.tripleS.repository.StudentCurriculumRecordRepository;

@Service
public class StudentCurriculumRecordServiceImpl implements StudentCurriculumRecordService {

	@Autowired
	StudentCurriculumRecordRepository studentCurriculumRecordRepository;
	
	@Autowired
	StudentCourseDetailsRepository studentCourseDetailsRepository; 
	
	@Autowired
	EntityDetailsService entityDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(StudentCurriculumRecordServiceImpl.class);
	
	@Override
	public List<StudentCurriculumRecord> findByFileNo(String fileNo) {
		EntityDetails applicant = entityDetailsService.findApplicant(fileNo);
		List<StudentCourseDetails> studentCourseDetails = studentCourseDetailsRepository.findByEntityDetails(applicant);
		return studentCurriculumRecordRepository.findByStudentCourseDetailsIn(studentCourseDetails);
	}

	@Override
	public StudentCurriculumRecord save(StudentCurriculumRecord studentCurriculumRecord) {
		studentCurriculumRecord.setStudentCourseDetails(studentCourseDetailsRepository.saveAndFlush(studentCurriculumRecord.getStudentCourseDetails()));
		return studentCurriculumRecordRepository.save(studentCurriculumRecord);
	}

	@Override
	public void delete(int id) {
		boolean deleteCourseAsWell = false;
		int courseID = 0;
		StudentCurriculumRecord studentCurriculumRecord = findById(id);
		if(studentCurriculumRecordRepository.findByStudentCourseDetails
				(studentCurriculumRecord.getStudentCourseDetails()).size() == 1) {
			deleteCourseAsWell = true;
			courseID = studentCurriculumRecord.getStudentCourseDetails().getId();
			logger.info("Course as well has to be deleted");
		}
		studentCurriculumRecordRepository.delete(id);
		
		if(deleteCourseAsWell){
			logger.info("Deleting course with id " + courseID);
			studentCourseDetailsRepository.delete(courseID);
		}
		
	}

	@Override
	public StudentCurriculumRecord findById(int id) {
		return studentCurriculumRecordRepository.findById(id);
	}

}
