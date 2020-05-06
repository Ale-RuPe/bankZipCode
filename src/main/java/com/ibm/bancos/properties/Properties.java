package com.ibm.bancos.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class Properties {
	
	@Value("${exceptions.message.notfound}")
	private String notFoundMsg;
	
	@Value("${exceptions.location.service}")
	private String locationServiceMsg;
	
	@Value("${controller.uri}")
	private String controllerUri;
	
	@Value("${exceptions.message.badrequest}")
	private String badRequestMsg;
	
	@Value("${requestParams.messages.missingParam}")
	private String missingRequestParamMsg;
	
	@Value("${exceptions.message.invalidRequestParam}")
	private String invalidRequestParam;
	
	@Value("${client.zipcode.host}")
	private String host;
	
	@Value("${client.zipcode.uri}")
	private String clientURI;

}