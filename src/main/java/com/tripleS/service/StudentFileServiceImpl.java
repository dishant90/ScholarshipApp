package com.tripleS.service;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
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
	public StudentFile findByFileNo(String fileNo) {
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
		String generatedFileId = generateFileId();
		while(studentFileRepository.existsByFileNo(generatedFileId)) {
		generatedFileId = generateFileId();
		}
        studentFile.setFileNo(generatedFileId);
        studentFile.setFileStatus("New");
        studentFile.setCreatedBy(auth.getName());
        studentFile.setCreatedDate(new Date());
        //studentFile.setCreatedDate(new Date(new java.util.Date().getTime()));
        studentFile.getEntityDetails().setType("Applicant");
        studentFile = studentFileRepository.save(studentFile);
		return studentFile;
	}

	@Override
	public boolean existsByFileNo(String fileNo) {
		return studentFileRepository.existsByFileNo(fileNo);
	}
	private String generateFileId(){
		String fileId = new String(RandomStringUtils.randomAlphanumeric(8).toUpperCase());
		return fileId;
	}
}
