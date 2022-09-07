package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.noname.uol.entidades.Categorias;

public interface CategoriaRepositorio extends MongoRepository<Categorias, String>{

}
