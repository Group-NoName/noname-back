package com.noname.uol.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Servicos;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.repositorios.CategoriaRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class CategoriasServico {
	
	@Autowired
	private CategoriaRepositorio repositorio;
	
	public List<Servicos> findAll(){
		return repositorio.findAll();
	}
	
	
	public Servicos findById(String id) {
		Optional<Servicos> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrado"));
	}
	
	public List<Produtos> getAllProducts(Servicos categoria){
		return categoria.getProdutos();
	}

	public Servicos insert(Servicos obj) {
		return repositorio.save(obj);
	}
	public Servicos update(Servicos obj) {
		Servicos newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	public Servicos body(Servicos obj) {
		return new Servicos(obj.getId(),obj.getNome(), obj.getProdutos());
	}
	private void updateData(Servicos newObj, Servicos obj) {
		newObj.setNome(obj.getNome());
		newObj.getProdutos();
	}
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
	
}
