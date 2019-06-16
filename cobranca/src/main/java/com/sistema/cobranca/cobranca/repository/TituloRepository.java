package com.sistema.cobranca.cobranca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sistema.cobranca.cobranca.model.Titulo;

@Repository
public interface TituloRepository extends CrudRepository<Titulo, Long>{
	
}