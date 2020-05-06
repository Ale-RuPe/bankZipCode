package com.ibm.bancos.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.ibm.bancos.dao.BancosDAO;
import com.ibm.bancos.entity.Banco;
import com.ibm.bancos.model.BancoModel;
import com.ibm.bancos.properties.Properties;
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
	Properties prop;
	
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
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<List<Banco>> requestEntity = new HttpEntity<List<Banco>>(headers);
		RestTemplate template = new RestTemplate();
		ParameterizedTypeReference<List<Banco>> responseType = new ParameterizedTypeReference<List<Banco>>(){};
		
		String url = prop.getHost()+prop.getClientURI()+zipCode;
		List<Banco> response = template
				.exchange(url, HttpMethod.GET, requestEntity, responseType).getBody();
		
		log.info("Returning fallback response {}", response.size());
		return response.stream().map(locate::createResponse).collect(Collectors.toList());
	}
}
