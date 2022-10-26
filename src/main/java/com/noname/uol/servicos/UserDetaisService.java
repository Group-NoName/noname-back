package com.noname.uol.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.noname.uol.config.jwt.UserDetailsImpl;
import com.noname.uol.config.jwt.UsuarioSelecionadorEmailUsuario;
import com.noname.uol.entidades.Credencial;
import com.noname.uol.entidades.UserModel;
import com.noname.uol.repositorios.UserRepositorio;


@Service
public class UserDetaisService implements UserDetailsService{
	@Autowired
	private UserRepositorio repositorio;
	@Autowired
	private UsuarioSelecionadorEmailUsuario selecionador;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<UserModel> usuarios = repositorio.findAll();
		UserModel usuario = selecionador.select(usuarios, email);
		if (usuario == null) {
			throw new UsernameNotFoundException(email);
		}
		Credencial credencial = usuario.getCredencial();
		return new UserDetailsImpl(credencial.getEmail(), credencial.getPassword(), usuario.getAutenticacao());
	}
}
