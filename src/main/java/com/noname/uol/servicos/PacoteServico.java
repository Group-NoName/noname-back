package com.noname.uol.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Pacotes;
import com.noname.uol.repositorios.PacotesRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class PacoteServico {
	@Autowired
	private PacotesRepositorio repositorio;
	
	public Pacotes save(Pacotes pacote) {
		return repositorio.save(pacote);
	}
	public List<Pacotes> findAll(){
		return repositorio.findAll();
	}
	public Pacotes findById(String id) {
		Optional<Pacotes> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pacote não encontrado"));
	}
	public Pacotes insert(Pacotes obj) {
		return repositorio.save(obj);
	}
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
	public Pacotes update(Pacotes obj) {
		Pacotes newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	public void updateData(Pacotes newObj, Pacotes obj) {
		newObj.setNome(obj.getNome());
		newObj.setProdutos(obj.getProdutos());
	}
}
