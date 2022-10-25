package com.noname.uol.entidades;


import java.io.Serializable;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import com.noname.uol.entidades.UserModel;
import lombok.Data;

@Data
public class UserModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String email;
	private String password;
	private List<Role> autenticacao;	
}
