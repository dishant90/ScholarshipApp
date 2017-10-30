package com.tripleS.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.tripleS.repository.StudentFileRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentFileServiceTest {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentFileServiceTest.class);
	
	@Autowired
    StudentFileRepository studentFileRepository;
	
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
