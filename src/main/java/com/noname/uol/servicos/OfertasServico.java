package com.noname.uol.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Ofertas;
import com.noname.uol.repositorios.OfertasRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class OfertasServico {

	@Autowired
	private OfertasRepositorio repositorio;
	
	public List<Ofertas> findAll(){
		return repositorio.findAll();
	}
	public Ofertas insert(Ofertas obj) {
		return repositorio.save(obj);
	}
	public Ofertas findById(String id) {
		Optional<Ofertas> obj =  repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Oferta não encontrado"));
	}
	public void save(Ofertas oferta) {
		repositorio.save(oferta);
	}
}
