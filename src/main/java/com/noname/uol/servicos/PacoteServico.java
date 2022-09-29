package com.noname.uol.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Pacotes;
import com.noname.uol.repositorios.PacotesRepositorio;

@Service
public class PacoteServico {
	@Autowired
	private PacotesRepositorio repositorio;
	
	public Pacotes save(Pacotes pacote) {
		return repositorio.save(pacote);
	}
	public List<Pacotes> obterPacotes(){
		return repositorio.findAll();
	}
}
