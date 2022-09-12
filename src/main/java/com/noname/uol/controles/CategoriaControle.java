package com.noname.uol.controles;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.noname.uol.servicos.ProdutoServico;

@CrossOrigin
@RestController
@RequestMapping("/categoria")
public class CategoriaControle {
	
	
	@Autowired
	private CategoriasServico service;
	@Autowired
	private CategoriaRepositorio repo;
	@Autowired
	private ProdutoServico serviceProduto;
	
	@GetMapping("/categorias")
	public ResponseEntity<List<Categorias>> getAllCategorias(){
		List<Categorias> categorias = service.findAll();
		return ResponseEntity.ok().body(categorias);
	}
	
	@GetMapping("/categorias/{id}")
	public ResponseEntity<Categorias> getCategoriaById(@PathVariable String id){
		Categorias categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping("/categorias/{id}/produtos")
	public ResponseEntity<List<Produtos>> findCategoriaProduto(@PathVariable String id) {
		Categorias categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria.getProdutos());
	}
	
	@PostMapping("/categorias")
	public ResponseEntity<Void> insertNewCategoria(@RequestBody Categorias categoria){
		Categorias obj = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/categorias-produtos/{id}")
	public ResponseEntity<Void> insertProduto(
			@RequestBody Categorias objDto,
			@PathVariable String id){
		Categorias categoria = service.findById(id);
		categoria.getProdutos().addAll(objDto.getProdutos());
		repo.save(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/categorias-produtos/{categoriaId}/{produtoId}")
	public ResponseEntity<Void> deleteRelacao(
			@PathVariable String categoriaId,
			@PathVariable String produtoId){
		Categorias categoria = service.findById(categoriaId);
		Produtos produto = serviceProduto.findById(produtoId);
		categoria.getProdutos().remove(produto);
		repo.save(categoria);
		return ResponseEntity.noContent().build();
	}
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
