package com.ibm.bancos.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.bancos.model.BancoModel;
import com.ibm.bancos.model.exceptions.BadRequestException;
import com.ibm.bancos.service.BancosZipCodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BancosZipCodeController {
	@Autowired
	BancosZipCodeService service;
	
	@GetMapping("${controller.uri}")
	public ResponseEntity<List<BancoModel>> getBancos(
			@PathVariable String postalcode) throws MissingServletRequestParameterException,BadRequestException {

		log.info("Controller values {}",postalcode);
		
		if(!Pattern.matches("\\d{5}",postalcode)) {
			throw new BadRequestException("${exceptions.message.badrequest}", "${exceptions.location.controller}", "${controller.uri}");
		}
		List<BancoModel> response = service.findBancos(postalcode);
		log.info("Bancos {}",response.size());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
