package com.tripleS.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.tripleS.enums.FileStatusEnum;
import com.tripleS.model.QStudentFile;
import com.tripleS.model.SearchStudentFile;
import com.tripleS.model.StudentFile;
import com.tripleS.repository.StudentFileRepository;

@Service
public class SearchStudentFileServiceImpl implements SearchStudentFileService {

	@Autowired
    StudentFileRepository studentFileRepository;

	private static final Logger logger = LoggerFactory.getLogger(SearchStudentFileServiceImpl.class);
	
	@Override
	public List<StudentFile> search(SearchStudentFile searchStudentFile) {
		
		BooleanExpression hasFileNo=null;
		BooleanExpression hasFileStatus = null;
		BooleanExpression hasMobileNo = null;
		BooleanExpression hasFirstName = null;
		BooleanExpression hasLastName = null;
		BooleanExpression hasCreatedBy = null;
		BooleanExpression hasInterviewedBy = null;
		BooleanExpression hasApproxCreatedDate=null;
		BooleanExpression hasApproxInterviewedDate = null;
		
		//collection to hold list of applicable query filters
		List<BooleanExpression> applicantSearchCriteria = new ArrayList<BooleanExpression>();

		//building predicate based on applicable query filters
		BooleanBuilder studentFileFilter = new BooleanBuilder();
		
		String fileNo = searchStudentFile.getFileNo();
		FileStatusEnum fileStatus = searchStudentFile.getFileStatus();
		String mobileNo = searchStudentFile.getMobileNo();
		String firstName = searchStudentFile.getFirstName();
		String lastName = searchStudentFile.getLastName();
		String createdBy = searchStudentFile.getCreatedBy();
		String interviewedBy = searchStudentFile.getInterviewedBy();
		Date createdDate = searchStudentFile.getCreatedDate();
		Date interviewedDate = searchStudentFile.getInterviewedDate();
		
		try {
			//Query DSL SQL Query helper object
			QStudentFile studentFile = QStudentFile.studentFile;
			
			// add the filters into the collection
			if(fileNo != null && !fileNo.trim().isEmpty()){
				hasFileNo = studentFile.fileNo.eq(fileNo);
				applicantSearchCriteria.add(hasFileNo);
				logger.info("Filter File No Added");
			}
			if(fileStatus != null){
				hasFileStatus = studentFile.fileStatus.eq(fileStatus);
				applicantSearchCriteria.add(hasFileStatus);
				logger.info("Filter File Status Added");
			}
			if(mobileNo != null && !mobileNo.trim().isEmpty()){
				hasMobileNo = studentFile.entityDetails.mobileNo.eq(mobileNo);
				applicantSearchCriteria.add(hasMobileNo);
				logger.info("Filter Mobile Number Added");
			}
			if(firstName != null && !firstName.trim().isEmpty()){
				hasFirstName = studentFile.entityDetails.firstName.eq(firstName);
				applicantSearchCriteria.add(hasFirstName);
				logger.info("Filter First Name Added");
			}
			if(lastName != null && !lastName.trim().isEmpty()){
				hasLastName = studentFile.entityDetails.lastName.eq(lastName);
				applicantSearchCriteria.add(hasLastName);
				logger.info("Filter Last Name Added");
			}
			if(createdBy != null && !createdBy.trim().isEmpty()){
				hasCreatedBy = studentFile.createdBy.eq(createdBy);
				applicantSearchCriteria.add(hasCreatedBy);
				logger.info("Filter Created By Added");
			}
			if(interviewedBy != null && !interviewedBy.trim().isEmpty()){
				hasInterviewedBy = studentFile.interviewedBy.eq(interviewedBy);
				applicantSearchCriteria.add(hasInterviewedBy);
				logger.info("Filter Interviewed By Added");
			}
			if(createdDate != null) {
				hasApproxCreatedDate = studentFile.createdDate.between(DateUtils.addDays(createdDate, -3), DateUtils.addDays(createdDate, 3));
				applicantSearchCriteria.add(hasApproxCreatedDate);
				logger.info("Filter File No added");
			}
			if(interviewedDate != null){
				hasApproxInterviewedDate = studentFile.interviewedDate.eq(interviewedDate);
				applicantSearchCriteria.add(hasApproxInterviewedDate);
				logger.info("Filter File No added");
			}
			
			//form the predicate to be used in where clause
			for(int i=0; i<applicantSearchCriteria.size(); i++){
				studentFileFilter.and(applicantSearchCriteria.get(i));
			}
			logger.info("Predicate formed: " + studentFileFilter.toString());
			
			List<StudentFile> studentFilesFound = (List<StudentFile>) studentFileRepository.findAll(studentFileFilter);
			return studentFilesFound;
		}
		catch(Exception ex) {
			logger.error("Exception message: " + ex.getMessage());
			return null;
		}
	}
	
}
