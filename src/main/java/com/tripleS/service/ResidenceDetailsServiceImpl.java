package com.tripleS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tripleS.model.EntityDetails;
import com.tripleS.model.ResidenceDetails;
import com.tripleS.repository.ResidenceDetailsRepository;

@Service
public class ResidenceDetailsServiceImpl implements ResidenceDetailsService {

	@Autowired
    ResidenceDetailsRepository residenceDetailsRepository;
	
	@Autowired
	EntityDetailsService entityDetailsService;
	
	
	@Override
	public ResidenceDetails findByFileNo(String fileNo) {
		EntityDetails applicant = entityDetailsService.findApplicant(fileNo);
		return residenceDetailsRepository.findByEntityDetails(applicant);
	}

	@Override
	public ResidenceDetails save(ResidenceDetails residenceDetails) {
		return residenceDetailsRepository.save(residenceDetails);
	}

	@Override
	public ResidenceDetails findById(int id) {
		return residenceDetailsRepository.findById(id);
	}

}
