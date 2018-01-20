package com.tripleS.classLevelConstraints;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.model.SearchStudentFile;

public class SearchStudentFileValidator implements ConstraintValidator<ValidateSearchStudentFile, SearchStudentFile> {

	private static final Logger logger = LoggerFactory.getLogger(SearchStudentFileValidator.class);

	@Override
	public void initialize(ValidateSearchStudentFile arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(SearchStudentFile searchStudentFile, ConstraintValidatorContext context) {

		if (searchStudentFile == null || (StringUtils.isBlank(searchStudentFile.getFileNo())
				&& searchStudentFile.getFileStatus()== null
				&& StringUtils.isBlank(searchStudentFile.getFirstName())
				&& StringUtils.isBlank(searchStudentFile.getLastName())
				&& StringUtils.isBlank(searchStudentFile.getMobileNo())
				&& StringUtils.isBlank(searchStudentFile.getCreatedBy())
				&& StringUtils.isBlank(searchStudentFile.getInterviewedBy())
				&& searchStudentFile.getCreatedDate() == null && searchStudentFile.getInterviewedDate() == null)) {
			logger.error("No search criteria is provided for searching student case");
			context.buildConstraintViolationWithTemplate("*Please provide atleast one search criteria")
					.addPropertyNode("fileNo").addConstraintViolation();
			return false;
		}
		return true;
	}

}
