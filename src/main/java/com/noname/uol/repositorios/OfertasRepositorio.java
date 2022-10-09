package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.noname.uol.entidades.Ofertas;

public interface OfertasRepositorio extends MongoRepository<Ofertas, String>{

}
