package com.ibm.bancos.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class HeaderProperties {
	
	@Value("${header.accept.name}")
	private String acceptHeader;
	
	@Value("${header.uuid.name}")
	private String uuidHeader;
	
	@Value("${header.countryCode.name}")
	private String countryCodeHeader;
	
	@Value("${header.channelId.name}")
	private String channelidHeader;

	@Value("${header.businessCode.name}")
	private String businessCodeHeader;

	@Value("${header.messages.missingHeader}")
	private String msgMissingHeader;
	
	@Value("${exceptions.message.invalidHeader}")
	private String invalidHeaderMsg;
	
}
