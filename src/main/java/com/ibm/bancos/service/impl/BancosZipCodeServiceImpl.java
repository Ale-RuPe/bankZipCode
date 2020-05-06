package com.ibm.bancos.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.bancos.dao.BancosDAO;
import com.ibm.bancos.model.BancoModel;
import com.ibm.bancos.model.exceptions.NotFoundException;
import com.ibm.bancos.properties.Properties;
import com.ibm.bancos.service.BancosZipCodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BancosZipCodeServiceImpl implements BancosZipCodeService{
	@Autowired
	BancosDAO dao;
	
	@Autowired
	Properties properties;
	
	@Override
	public List<BancoModel> findBancos(String zipCode) {
		log.info("BancosZipCodeService findBancos {}", zipCode);
		
		List<BancoModel> response = dao.findByZipCode(zipCode);
		if(response.isEmpty()) {
			throw new NotFoundException(
					properties.getNotFoundMsg(), 
					properties.getLocationServiceMsg(), 
					properties.getControllerUri()
			);
		}
		log.info("Retrieving {}",response.size());
		return response;
	}

}
