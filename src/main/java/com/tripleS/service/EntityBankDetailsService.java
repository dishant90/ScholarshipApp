package com.tripleS.service;

import java.util.List;
import com.tripleS.model.EntityBankDetails;

public interface EntityBankDetailsService {
	List<EntityBankDetails> findByFileNo(int fileNo);
	EntityBankDetails save(EntityBankDetails entityBankDetails);
	void delete(int id);
	EntityBankDetails findById(int id);
}
