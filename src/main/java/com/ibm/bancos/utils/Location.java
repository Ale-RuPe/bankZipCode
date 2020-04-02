package com.ibm.bancos.utils;


import com.ibm.bancos.entity.Banco;
import com.ibm.bancos.model.BancoModel;

public interface Location {
	public boolean isNear(String zipCode, Banco banco);
	
	public BancoModel createResponse(Banco banco);
}
