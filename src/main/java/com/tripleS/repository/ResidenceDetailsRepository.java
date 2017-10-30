package com.tripleS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripleS.model.EntityDetails;
import com.tripleS.model.ResidenceDetails;

public interface ResidenceDetailsRepository extends JpaRepository<ResidenceDetails, Integer> {
	ResidenceDetails findById(int id);
	ResidenceDetails findByEntityDetails(EntityDetails entityDetails);
}
