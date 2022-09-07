package com.noname.uol.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.noname.uol.entidades.Categorias;
import com.noname.uol.repositorios.CategoriaRepositorio;

@CrossOrigin
@RestController
public class CategoriaControle {
	
	
	@Autowired
	private CategoriaRepositorio repositorio;
	
	@GetMapping("/categorias")
	public List<Categorias> obterCategorias(){
		List<Categorias> categorias = repositorio.findAll();
		return categorias;
	}
	@PostMapping("/nova-categoria")
	public void cadastrarCategoria(@RequestBody Categorias categorias) {
		repositorio.insert(categorias);
	}
}
