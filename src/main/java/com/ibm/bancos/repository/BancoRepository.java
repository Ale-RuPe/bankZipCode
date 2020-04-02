package com.ibm.bancos.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.bancos.entity.Banco;

public interface BancoRepository extends MongoRepository<Banco, String> {
	List<Banco> findAll();
}
