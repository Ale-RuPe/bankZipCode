package com.ibm.bancos.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ibm.bancos.entity.Banco;

@FeignClient(name = "${client.zipcode.name}",  url = "${client.zipcode.url}", fallback = BancosZipCodeFallback.class)
public interface BancosZipCodeClient {
	
	@GetMapping("${client.zipcode.uri}"+"${client.zipcode.path-variable}")
	List<Banco> retrieveZipCodeBanks(@PathVariable String zipcode);
}

