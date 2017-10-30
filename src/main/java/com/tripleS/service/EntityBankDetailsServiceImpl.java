package com.tripleS.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tripleS.model.EntityBankDetails;
import com.tripleS.model.EntityDetails;
import com.tripleS.repository.EntityBankDetailsRepository;

@Service
public class EntityBankDetailsServiceImpl implements EntityBankDetailsService {

	@Autowired
    EntityBankDetailsRepository entityBankDetailsRepository;
	
	@Autowired
	EntityDetailsService entityDetailsService;
	
	
	@Override
	public List<EntityBankDetails> findByFileNo(String fileNo) {
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
