package com.tripleS.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tripleS.model.EntityAddressDetails;
import com.tripleS.model.EntityBankDetails;
import com.tripleS.model.EntityDetails;
import com.tripleS.repository.EntityAddressDetailsRepository;
import com.tripleS.repository.EntityBankDetailsRepository;
import com.tripleS.repository.EntityDetailsRepository;
import com.tripleS.repository.StudentFileRepository;

@Service
public class EntityBankDetailsServiceImpl implements EntityBankDetailsService {

	@Autowired
    EntityBankDetailsRepository entityBankDetailsRepository;
	
	@Autowired
    StudentFileRepository studentFileRepository;
	
	@Autowired
	EntityDetailsService entityDetailsService;
	
	
	@Override
	public List<EntityBankDetails> findByFileNo(int fileNo) {
		EntityDetails applicant = entityDetailsService.findApplicant(fileNo);
		return entityBankDetailsRepository.findByEntityDetails(applicant);
	}

	@Override
	public EntityBankDetails save(EntityBankDetails entityBankDetails) {
		return entityBankDetailsRepository.save(entityBankDetails);
	}

	@Override
	public void delete(int id) {
		entityBankDetailsRepository.delete(id);
	}

	@Override
	public EntityBankDetails findById(int id) {
		return entityBankDetailsRepository.findById(id);
	}

}
