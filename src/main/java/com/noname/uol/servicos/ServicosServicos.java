package com.noname.uol.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Servicos;
import com.noname.uol.repositorios.ServiceRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class ServicosServicos {

	@Autowired
	private ServiceRepositorio repositorio;
	
	private void updateData(Servicos newObj, Servicos obj) {
		newObj.setNome(obj.getNome());
		newObj.setProdutos(obj.getProdutos());
	}
	private void updateDataProdutos(Servicos newObj, Servicos obj) {
		newObj.setProdutos(obj.getProdutos());
	}
	
	public Servicos insert(Servicos obj) {
		return repositorio.save(obj);
	}
	
	public Servicos findById(String id) {
		Optional<Servicos> find = repositorio.findById(id);
		return find.orElseThrow(() -> new ObjectNotFoundException("Servico não encontrado"));
	}
	
	public List<Servicos> findAll(){
		return repositorio.findAll();
	}
	public Servicos update(Servicos obj) {
		Servicos newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	public Servicos updateProdutos(Servicos obj) {
		Servicos newObj = findById(obj.getId());
		updateDataProdutos(newObj, obj);
		return repositorio.save(newObj);
	}
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
}