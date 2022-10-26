package com.noname.uol.config.jwt.token;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noname.uol.entidades.Credencial;

public class TokenAuthentication extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private TokenService jwtTokenGerador;

	public TokenAuthentication(AuthenticationManager authenticationManager, TokenService jwtTokenGerador) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenGerador = jwtTokenGerador;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			Credencial credencialDto = new ObjectMapper().readValue(request.getInputStream(),
					Credencial.class);
			UsernamePasswordAuthenticationToken tokenAuthentication = new UsernamePasswordAuthenticationToken(
					credencialDto.getEmail(), credencialDto.getPassword(), new ArrayList<>());

			Authentication authentication = authenticationManager.authenticate(tokenAuthentication);
			return authentication;

		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication resultadoAutenticacao) throws IOException, ServletException {
		UserDetails userSpring = (UserDetails) resultadoAutenticacao.getPrincipal();
		String emailUser = userSpring.getUsername();
		boolean jwtToken = jwtTokenGerador.validateToken(emailUser);
		response.addHeader("Authorization", "Bearer " + jwtToken);
	}
	
}
