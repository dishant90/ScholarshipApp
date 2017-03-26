package com.tripleS.service;

import java.sql.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripleS.model.EntityDetails;
import com.tripleS.model.StudentFile;
import com.tripleS.repository.StudentFileRepository;

//import com.tripleS.model.StudentFileDTO;
//import com.tripleS.repository.StudentApplicationDAO;

@Controller
@RequestMapping("/studentApplication")
public class StudentApplicationService {

    /*@Autowired
    StudentApplicationDAO studentApplication;*/
	@Autowired
    StudentFileRepository studentFileRepository;
    
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody boolean createStudentFile() {
    	/*StudentFileDTO studentFile = new StudentFileDTO("1", "New", 1001, "John", new Date(new java.util.Date().getTime()));
    	studentApplication.createStudentEntity();
    	studentApplication.createStudentFile(studentFile);*/
    	
    	StudentFile sf1 = new StudentFile();
        EntityDetails ed1 = new EntityDetails();
        
        sf1.setFileNo("2");
        sf1.setFileStatus("New");
        sf1.setCreatedBy("Doe");
        sf1.setCreatedDate(new Date(new java.util.Date().getTime()));
        ed1.setType("Applicant");
        ed1.setFirstName("Doe");
        
        sf1.setEntityDetails(ed1);
        
        studentFileRepository.save(sf1);
        return true;
    }

}