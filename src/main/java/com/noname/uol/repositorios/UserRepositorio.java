package com.noname.uol.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.noname.uol.entidades.UserModel;

@Repository
public interface UserRepositorio extends MongoRepository<UserModel, String>{
	
}
