package com.tripleS.Service;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tripleS.controller.StudentApplicationController;
import com.tripleS.model.EntityDetails;
import com.tripleS.repository.StudentFileRepository;
import com.tripleS.service.EntityDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentFileServiceTest {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentFileServiceTest.class);
	
	@Autowired
    StudentFileRepository studentFileRepository;
	
	@Autowired
	private EntityDetailsService entityDetailsService;
	
	@Test
	public void maxStudentFilesTest() {
		
	}
	
	@Test
	public void getEntityDetailsTest() {
		// find applicant by file #
		//EntityDetails applicant = entityDetailsService.findApplicant("1");
		//logger.info(applicant.toString());
	}
}
