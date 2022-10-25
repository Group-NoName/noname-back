package com.noname.uol.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noname.uol.entidades.UserModel;
import com.noname.uol.feignClient.SecurityAPI;
import com.noname.uol.servicos.userService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SecurityAPI api;
	
	@Autowired
	private userService service;
	
	@GetMapping("/home")
	public ResponseEntity<?> getTeste() {
		return new ResponseEntity<>(api.getUser(), HttpStatus.ACCEPTED);
	}
	@PostMapping("/cadastro")
	public ResponseEntity<?> Cadastrouser(@RequestBody UserModel obj){
		return new ResponseEntity<>(api.newUser(obj), HttpStatus.ACCEPTED);

	}
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserModel obj){
		return new ResponseEntity<>(api.loginUser(obj), HttpStatus.ACCEPTED);

	}

}
