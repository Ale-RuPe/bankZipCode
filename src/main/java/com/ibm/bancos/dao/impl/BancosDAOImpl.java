package com.ibm.bancos.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.bancos.dao.BancosDAO;
import com.ibm.bancos.entity.Banco;
import com.ibm.bancos.model.BancoModel;
import com.ibm.bancos.repository.BancoRepository;
import com.ibm.bancos.utils.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BancosDAOImpl implements BancosDAO{
	@Autowired
	BancoRepository repository;
	
	@Autowired
	Location locate;
	
	@Override
	public List<BancoModel> findByZipCode(String zipCode) {
		List<Banco> bancos = repository.findAll();
		log.info("dao finding by zipCode: {}",zipCode);
		
		List<BancoModel> s = bancos.stream()
				.filter( banco -> locate.isNear( zipCode, banco) )
				.map(locate::createResponse)
				.collect( Collectors.toList() );
		log.info("Retrieves?: {}", s.size());
		return s;
	}
}
