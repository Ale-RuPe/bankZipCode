package com.ibm.bancos.dao;

import java.util.List;

import com.ibm.bancos.model.BancoModel;


public interface BancosDAO {
	List<BancoModel> findByZipCode(String zipCode);
}
