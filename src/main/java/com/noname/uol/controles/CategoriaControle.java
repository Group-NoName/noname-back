package com.noname.uol.controles;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.noname.uol.entidades.Tags;
import com.noname.uol.repositorios.CategoriaRepositorio;
import com.noname.uol.servicos.CategoriasServico;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.excecao.TratamentoErro;

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
	public ResponseEntity<List<Categorias>> obterTodasCategorias(){
		List<Categorias> categorias = service.findAll();
		return ResponseEntity.ok().body(categorias);
	}
	
	@GetMapping("/categorias/{id}")
	public ResponseEntity<Categorias> obterCategoriaId(@PathVariable String id){
		Categorias categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping("/categorias/{id}/produtos")
	public ResponseEntity<List<Produtos>> ObterCategoriaProduto(@PathVariable String id) {
		Categorias categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria.getProdutos());
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirNovaCategoria(@RequestBody Categorias categoria){

		for (Categorias categoriaObj : service.findAll()) {
			if(categoriaObj.getNome().equals(categoria.getNome()))
				return new ResponseEntity<>("Nome de categoria não pode ser repetido", HttpStatus.CONFLICT);
		}
		Categorias obj = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return new ResponseEntity<>("Categoria cadastrada com sucesso", HttpStatus.CREATED);
		
	}
	
	@PutMapping("/categorias-produtos/{id}")
	public ResponseEntity<?> inserirProduto(
			@RequestBody Categorias objDto,
			@PathVariable String id){
		Categorias categoria = service.findById(id);
		
		for (Categorias categoriaObj : service.findAll()) {
			if(!Collections.disjoint(categoriaObj.getProdutos(), objDto.getProdutos())) {
				return new ResponseEntity<>("Produto já está em outra categoria", HttpStatus.NOT_ACCEPTABLE);
			}
		}
<<<<<<< Updated upstream
		boolean hasCopy = false;
		String errorLog = "";
		
		for (Produtos produto : objDto.getProdutos()) {
		
			if(categoria.getProdutos().contains(produto)) {
				hasCopy = true;
				errorLog += serviceProduto.findById(produto.getId()).getNome();
			}
		}
		
		if(hasCopy) {
			return new ResponseEntity<>(errorLog, HttpStatus.NOT_ACCEPTABLE);
=======
		
		TratamentoErro<Produtos> tratamentoErros = new TratamentoErro<Produtos>();
		
		tratamentoErros.verificarCopiaEntreListas(objDto.getProdutos(), categoria.getProdutos());

		if(tratamentoErros.getHasError()) {
			return new ResponseEntity<>(tratamentoErros.getErrorLog(), HttpStatus.NOT_ACCEPTABLE);
>>>>>>> Stashed changes
		}
		else {
			categoria.getProdutos().addAll(objDto.getProdutos());
			repo.save(categoria);
			return new ResponseEntity<>("Produtos inseridos com sucesso", HttpStatus.CREATED);
		}
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> update(
			@PathVariable String id,
			@RequestBody Categorias categorias){
		Categorias ids = service.body(categorias);
		ids.setId(id);
		ids = service.update(ids);
		return new ResponseEntity<>("Categoria atualizada com sucesso", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/categorias-produtos/{categoriaId}/{produtoId}")
	public ResponseEntity<?> deletarRelacaoCategoriaProduto(
			@PathVariable String categoriaId,
			@PathVariable String produtoId){
		Categorias categoria = service.findById(categoriaId);
		Produtos produto = serviceProduto.findById(produtoId);
		categoria.getProdutos().remove(produto);
		repo.save(categoria);
		return new ResponseEntity<>("Produtos removidos da categoria com sucesso", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletarCategoria(@PathVariable String id){
		service.delete(id);
		return new ResponseEntity<>("Categoria removida com sucesso", HttpStatus.ACCEPTED);
	}
	
}
