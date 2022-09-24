package com.noname.uol.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Categorias;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.repositorios.CategoriaRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class CategoriasServico {
	
	@Autowired
	private CategoriaRepositorio repositorio;
	
	public List<Categorias> findAll(){
		return repositorio.findAll();
	}
	
	
	public Categorias findById(String id) {
		Optional<Categorias> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrado"));
	}
	
	public List<Produtos> getAllProducts(Categorias categoria){
		return categoria.getProdutos();
	}

	public Categorias insert(Categorias obj) {
		return repositorio.save(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
}
