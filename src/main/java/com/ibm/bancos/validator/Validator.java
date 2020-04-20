package com.ibm.bancos.validator;

import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ibm.bancos.model.exceptions.BadRequestException;
import com.ibm.bancos.properties.HeaderProperties;
import com.ibm.bancos.properties.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Validator {
	
	private static final Predicate<String> emptyPredicate = StringUtils::isEmpty;
	
	@Autowired
	private HeaderProperties headerProperties;
	
	@Autowired
	private Properties properties;
	
	public void validateHeaders(Map<String, String> headers) throws BadRequestException {
		log.info("Validating Headers");
		
		validateHeader(emptyPredicate.test(headers.get(headerProperties.getAcceptHeader())),
		        headerProperties.getAcceptHeader());
		
		validateHeader(emptyPredicate.test(headers.get(headerProperties.getUuidHeader())),
		        headerProperties.getUuidHeader());
		
		validateHeader(emptyPredicate.test(headers.get(headerProperties.getCountryCodeHeader())),
		        headerProperties.getCountryCodeHeader());
		
		validateHeader(emptyPredicate.test(headers.get(headerProperties.getChannelidHeader())),
		        headerProperties.getChannelidHeader());
		
		validateHeader(emptyPredicate.test(headers.get(headerProperties.getBusinessCodeHeader())),
		        headerProperties.getBusinessCodeHeader());
	}
	
	private void validateHeader(boolean isHeaderInvalid, String header) {
		if (isHeaderInvalid) {
			log.info("Invalid header {}", header);
			throw new BadRequestException(properties.getBadRequestMsg(), 
					headerProperties.getInvalidHeaderMsg(), properties.getControllerUri());
	    }
	}
	
	public void validateParam(String zipCode) {
		boolean notMatch = !Pattern.matches("\\d{5}", zipCode);
		validate(emptyPredicate.test(zipCode), notMatch);
	}
	
	private void validate(boolean isInvalid, boolean notMatch) {
		if(isInvalid || notMatch) {
			log.info("Invalid Request Param");
			throw new BadRequestException(properties.getBadRequestMsg(), 
					properties.getInvalidRequestParam(), properties.getControllerUri() );
		}
	}
	
}