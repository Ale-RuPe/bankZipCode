package com.ibm.bancos.utils.impl;


import org.springframework.stereotype.Component;

import com.ibm.bancos.entity.Banco;
import com.ibm.bancos.model.BancoModel;
import com.ibm.bancos.utils.Location;

@Component
public class LocationImpl implements Location{

	@Override
	public boolean isNear(String zipCode, Banco banco) {
		String cp = banco.getPropiedades().getDireccion2();		
		return cp.contains(zipCode) ? true : false;
	}

	@Override
	public BancoModel createResponse(Banco banco) {
		BancoModel response = new BancoModel();
		
		response.setDireccion(banco.getPropiedades().getDireccion());
		response.setEstado(banco.getPropiedades().getEstado());
		response.setHoraApertura(banco.getPropiedades().getHora_apertura());
		response.setHoraCierre(banco.getPropiedades().getHora_cierre());
		response.setNombre(banco.getPropiedades().getNombre());
		response.setTelefono(banco.getPropiedades().getTelefono());
		response.setTipoSucursal(banco.getPropiedades().getTipo_sucursal());
		
		return response;
	}
}
