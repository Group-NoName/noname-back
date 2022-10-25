package com.noname.uol.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.noname.uol.entidades.UserModel;

import feign.Headers;

@FeignClient(name="user", url="http://localhost:8080/")
public interface SecurityAPI {
		
	@RequestMapping(method = RequestMethod.GET, value = "/user/home")
	@Headers("Content-Type: application/json")
	String getUser();
	
	@RequestMapping(method = RequestMethod.POST, value = "/user/cadastro", consumes = "application/json")
	String newUser(@RequestBody UserModel obj);
	
	@RequestMapping(method = RequestMethod.POST, value = "/user/login", consumes = "application/json")
	String loginUser(@RequestBody UserModel obj);

}
