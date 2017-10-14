package com.tripleS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripleS.model.EntityBankDetails;
import com.tripleS.model.EntityDetails;

public interface EntityBankDetailsRepository extends JpaRepository<EntityBankDetails, Integer> {

	EntityBankDetails findById(int id);

	List<EntityBankDetails> findByEntityDetails(EntityDetails entityDetails);
}
