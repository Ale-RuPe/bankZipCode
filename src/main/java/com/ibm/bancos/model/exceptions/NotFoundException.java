package com.ibm.bancos.model.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private final String location; //the location of the error
	private String uri;
	
	public NotFoundException(String message, String location,String uri) {
	    super(message);
	    this.location = location;
	    this.uri = uri;
	}
}