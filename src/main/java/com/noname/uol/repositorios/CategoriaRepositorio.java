package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.noname.uol.entidades.Servicos;

public interface CategoriaRepositorio extends MongoRepository<Servicos, String>{

}
