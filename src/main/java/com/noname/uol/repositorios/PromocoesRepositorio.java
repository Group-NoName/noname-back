package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.noname.uol.entidades.Promocoes;

@Repository
public interface PromocoesRepositorio extends MongoRepository<Promocoes, String>{
}
