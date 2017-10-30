package com.tripleS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripleS.model.EntityAddressDetails;
import com.tripleS.repository.EntityAddressDetailsRepository;

@Service
public class EntityAddressDetailsServiceImpl implements EntityAddressDetailsService {

	@Autowired
    EntityAddressDetailsRepository entityAddressDetailsRepository;

	@Override
	public void delete(EntityAddressDetails entityAddressDetails) {
		entityAddressDetailsRepository.delete(entityAddressDetails);
	}

}
