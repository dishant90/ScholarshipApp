package com.tripleS.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tripleS.model.EntityAddressDetails;
import com.tripleS.model.EntityDetails;
import com.tripleS.repository.EntityAddressDetailsRepository;
import com.tripleS.repository.EntityDetailsRepository;
import com.tripleS.repository.StudentFileRepository;

@Service
public class EntityDetailsServiceImpl implements EntityDetailsService {

	@Autowired
    EntityDetailsRepository entityDetailsRepository;
	
	@Autowired
    StudentFileRepository studentFileRepository;
	
	@Autowired
	EntityAddressDetailsRepository entityAddressDetailsRepository;
	
	@Override
	public List<EntityDetails> findRelativesByFileNo(String fileNo) {
		EntityDetails applicant = findApplicant(fileNo);
		return applicant.getRelatives();
	}

	@Override
	public EntityDetails findApplicant(String fileNo) {
		EntityDetails applicant = entityDetailsRepository.findById(studentFileRepository.findByFileNo(fileNo).getEntityDetails().getId());
		return applicant;
	}

	@Override
	public EntityDetails save(EntityDetails entityDetails) {
		return entityDetailsRepository.save(entityDetails);
	}

	@Override
	public void delete(int id) {
		EntityDetails entityDetails = entityDetailsRepository.findById(id);
		List<EntityDetails> relatives = entityDetails.getRelatives();
		if(relatives != null)
			if(relatives.size() > 0) {
				for(int i = 0; i < relatives.size(); i++) {
					if(relatives.get(i).getEntityAddressDetails() != null) {
						EntityAddressDetails entityAddressDetails = relatives.get(i).getEntityAddressDetails();
						entityAddressDetailsRepository.delete(entityAddressDetails);
					}
					entityDetailsRepository.delete(relatives.get(i));
				}
			}
		if(entityDetailsRepository.findById(id) != null)
			entityDetailsRepository.delete(id);
	}

	@Override
	public EntityDetails findById(int id) {
		return entityDetailsRepository.findById(id);
	}

}
