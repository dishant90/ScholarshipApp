package com.tripleS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripleS.model.EntityDetails;
import com.tripleS.model.StudentCourseDetails;
import com.tripleS.model.StudentCurriculumRecord;

public interface StudentCourseDetailsRepository extends JpaRepository<StudentCourseDetails, Integer> {

	StudentCourseDetails findById(int id);

	List<StudentCourseDetails> findByEntityDetails(EntityDetails applicant);

}
