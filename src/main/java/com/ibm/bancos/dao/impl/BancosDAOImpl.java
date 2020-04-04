package com.ibm.bancos.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibm.bancos.client.BancosZipCodeClient;
import com.ibm.bancos.dao.BancosDAO;
import com.ibm.bancos.entity.Banco;
import com.ibm.bancos.model.BancoModel;
import com.ibm.bancos.repository.BancoRepository;
import com.ibm.bancos.utils.Location;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BancosDAOImpl implements BancosDAO{
	@Autowired
	BancoRepository repository;
	
	@Autowired
	Location locate;
	
	@Autowired
	BancosZipCodeClient feignClient;
	
	
	@HystrixCommand(fallbackMethod = "findByZipCodeFallback")
	@Override
	public List<BancoModel> findByZipCode(String zipCode) {
		log.info("dao finding by zipCode: {}",zipCode);
		List<Banco> bancos = repository.findAll();
		
		List<BancoModel> s = bancos.stream()
				.filter( banco -> locate.isNear( zipCode, banco) )
				.map(locate::createResponse)
				.collect( Collectors.toList() );
		
		log.info("Retrieving fallback response {}", s.size());
		return s;
	}
	
	public List<BancoModel> findByZipCodeFallback(String zipCode){
		log.info("Fallback method with param: {}", zipCode);		
		
		List<Banco> bancos = feignClient.retrieveZipCodeBanks(zipCode);
		List<BancoModel> response = bancos.stream()
				.map(locate::createResponse)
				.collect(Collectors.toList());
		
		log.info("Returning fallback response {}", response.size());
		return response;
	}
}
