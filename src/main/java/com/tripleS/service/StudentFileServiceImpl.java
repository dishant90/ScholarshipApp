package com.tripleS.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tripleS.model.StudentFile;
import com.tripleS.repository.StudentFileRepository;

@Service
public class StudentFileServiceImpl implements StudentFileService {

	@Autowired
    StudentFileRepository studentFileRepository;
	
	@Override
	public StudentFile findByFileNo(int fileNo) {
		return studentFileRepository.findByFileNo(fileNo);
	}

	@Override
	public StudentFile update(StudentFile studentFile) {
		studentFile = studentFileRepository.save(studentFile);
		return studentFile;
	}

	@Override
	public StudentFile save(StudentFile studentFile) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int maxFileNo = 0;
		if(studentFileRepository.getMaxFileNo() != null) {
			maxFileNo = studentFileRepository.getMaxFileNo().intValue();	
    	}
        studentFile.setFileNo(maxFileNo + 1);
        studentFile.setFileStatus("New");
        studentFile.setCreatedBy(auth.getName());
        studentFile.setCreatedDate(new Date());
        //studentFile.setCreatedDate(new Date(new java.util.Date().getTime()));
        studentFile.getEntityDetails().setType("Applicant");
        studentFile = studentFileRepository.save(studentFile);
		return studentFile;
	}

	@Override
	public boolean existsByFileNo(int fileNo) {
		return studentFileRepository.existsByFileNo(fileNo);
	}
}
