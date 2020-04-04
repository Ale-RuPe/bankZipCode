package com.ibm.bancos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private String type;
	private String code;
	private String details;	
	private String location;
	private String moreInfo;
}
