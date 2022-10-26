package com.noname.uol.config.jwt;

import java.util.List;

import org.springframework.stereotype.Component;

import com.noname.uol.entidades.UserModel;

@Component
public class UsuarioSelecionadorEmailUsuario implements UserSelecionador<UserModel, String> {
	public UserModel select(List<UserModel> usuarios, String email) {
		UserModel selecionado = null;
		for (UserModel usuario : usuarios) {
			if (usuario.getCredencial().getEmail().equals(email)) {
				selecionado = usuario;
			}
		}
		return selecionado;
	}
}
