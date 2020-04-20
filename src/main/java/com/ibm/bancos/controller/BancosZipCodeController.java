package com.ibm.bancos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.bancos.model.BancoModel;
import com.ibm.bancos.model.exceptions.BadRequestException;
import com.ibm.bancos.service.BancosZipCodeService;
import com.ibm.bancos.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BancosZipCodeController {
	@Autowired
	Validator validator;
	
	@Autowired
	BancosZipCodeService service;
	
	@GetMapping("${controller.uri}")
	public ResponseEntity<List<BancoModel>> getBancos(@RequestHeader HttpHeaders httpHeaders,
			@PathVariable String postalcode) throws MissingServletRequestParameterException,BadRequestException {

		log.info("Headers {}", httpHeaders.toString());
		validator.validateHeaders(httpHeaders.toSingleValueMap());
		
		log.info("PathVariable {}",postalcode);
		validator.validateParam(postalcode);
		
		List<BancoModel> response = service.findBancos(postalcode);
		log.info("Bancos {}",response.size());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
