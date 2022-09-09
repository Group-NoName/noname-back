package com.noname.uol.controles;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.noname.uol.entidades.Categorias;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.repositorios.CategoriaRepositorio;
import com.noname.uol.servicos.CategoriasServico;

@CrossOrigin
@RestController
@RequestMapping("/categoria")
public class CategoriaControle {
	
	
	@Autowired
	private CategoriasServico service;
	@Autowired
	private CategoriaRepositorio repo;
	
	@GetMapping("/categorias")
	public ResponseEntity<List<Categorias>> getAllCategorias(){
		List<Categorias> categorias = service.findAll();
		return ResponseEntity.ok().body(categorias);
	}
	
	@GetMapping("/categoria/{id}")
	public ResponseEntity<Categorias> getCategoriaById(@PathVariable String id){
		Categorias categorias = service.findById(id);
		return ResponseEntity.ok().body(categorias);
	}
	
	@GetMapping("/categoria/{id}/produtos")
	public ResponseEntity<List<Produtos>> findCategoriaProduto(@PathVariable String id) {
		Categorias obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getProdutos());
	}
	
	@PostMapping("/categoria")
	public ResponseEntity<Void> insertNewCategoria(@RequestBody Categorias categoria){
		Categorias obj = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/categoria-produto/{id}")
	public ResponseEntity<Void> insertProduto(@RequestBody Categorias objDto, @PathVariable String id){
		Categorias categoria = service.findById(id);
		categoria.getProdutos().addAll(objDto.getProdutos());
		repo.save(categoria);
		return ResponseEntity.noContent().build();
	}
	
}
