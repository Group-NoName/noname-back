package com.noname.uol.servicos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.noname.uol.entidades.UserModel;
import com.noname.uol.repositorios.UserRepositorio;

@Service
public class userService{

	@Autowired
	private UserRepositorio repositorio;
	
	public UserModel createNewUser(UserModel obj) {
		return repositorio.insert(obj);
	}
	
}
