package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.noname.uol.entidades.Servicos;

@Repository
public interface ServiceRepositorio extends MongoRepository<Servicos, String>{

}
