package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.noname.uol.entidades.Tags;

@Repository
public interface TagsRepositorio extends MongoRepository<Tags, String> {

}
