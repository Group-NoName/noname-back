package com.noname.uol.repositorios;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.noname.uol.entidades.UserModel;

public interface UserRepositorio extends MongoRepository<UserModel, String>{
	Optional<UserModel> findByEmail(String email);

}
