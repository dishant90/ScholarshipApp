package com.tripleS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tripleS.model.StudentCourseDetails;
import com.tripleS.model.StudentCurriculumRecord;

public interface StudentCurriculumRecordRepository extends JpaRepository<StudentCurriculumRecord, Integer> {

	StudentCurriculumRecord findById(int id);

	List<StudentCurriculumRecord> findByStudentCourseDetailsIn(List<StudentCourseDetails> studentCourseDetails);
	
	List<StudentCurriculumRecord> findByStudentCourseDetails(StudentCourseDetails studentCourseDetails);
}
