package com.ibm.bancos.service;


import java.util.List;

import com.ibm.bancos.model.BancoModel;

public interface BancosZipCodeService {
	List<BancoModel> findBancos(String zipCode);
}
