package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.noname.uol.entidades.Pacotes;

@Repository
public interface PacotesRepositorio extends MongoRepository<Pacotes, String> {
 
}
