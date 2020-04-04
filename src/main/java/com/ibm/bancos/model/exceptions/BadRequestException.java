package com.ibm.bancos.model.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private String location;
	private String uri;
	
	public BadRequestException(String message, String location,String uri) {
	    super(message);
	    this.location = location;
	    this.uri = uri;
	}
}