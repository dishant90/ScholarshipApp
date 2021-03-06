package com.tripleS.service;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tripleS.enums.FileStatusEnum;
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

	/**
	 * should be used only when updating an existing case
	 * @see com.tripleS.service.StudentFileService#update(com.tripleS.model.StudentFile)
	 */
	@Override
	public StudentFile update(StudentFile studentFile) {
		studentFile = studentFileRepository.save(studentFile);
		return studentFile;
	}

	/**
	 * should be used only when creating a new case
	 * @see com.tripleS.service.StudentFileService#save(com.tripleS.model.StudentFile)
	 */
	@Override
	public StudentFile save(StudentFile studentFile) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String generatedFileNo = generateFileNo();
		while(studentFileRepository.existsByFileNo(generatedFileNo)) {
		generatedFileNo = generateFileNo();
		}
        studentFile.setFileNo(generatedFileNo);
        studentFile.setFileStatus(FileStatusEnum.NEW);
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
	private String generateFileNo(){
		String fileNo = new String(RandomStringUtils.randomAlphanumeric(8).toUpperCase());
		return fileNo;
	}
}
