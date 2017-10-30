package com.tripleS.service;

import com.tripleS.model.ResidenceDetails;

public interface ResidenceDetailsService {
	ResidenceDetails findByFileNo(String fileNo);
	ResidenceDetails save(ResidenceDetails residenceDetails);
	ResidenceDetails findById(int id);
}
