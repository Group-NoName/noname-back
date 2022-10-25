package com.noname.uol.entidades;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

import com.noname.uol.enums.Roles;

import lombok.Data;

@Data
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Roles role;

	
}
