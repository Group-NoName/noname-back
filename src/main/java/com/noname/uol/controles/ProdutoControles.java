package com.noname.uol.controles;

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


import com.noname.uol.dto.produtoDTO;
import com.noname.uol.entidades.TagProduto;
import com.noname.uol.entidades.Tags;
import com.noname.uol.recursos.ComparadorTagProduto;
import com.noname.uol.entidades.Categorias;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.TagsServico;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/produto")
public class ProdutoControles {
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@Autowired
	private TagsServico tagsServico;
	
	@GetMapping("/produtos")
	public ResponseEntity<List<produtoDTO>> obterProdutos() {
		List<Produtos> produto = produtoServico.findAll();
		List<produtoDTO> produtoDto = produto
									.stream()
									.map(x -> new produtoDTO(x))
									.collect(Collectors.toList());
		return ResponseEntity.ok().body(produtoDto);
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<produtoDTO> obterProdutoId(@PathVariable String id) {
		Produtos produto = produtoServico.findById(id);
		return  ResponseEntity.ok().body(new produtoDTO(produto));
	}
	
	@GetMapping("/produtos-semelhantes/{id}/{quantia}")
	public ResponseEntity<List<produtoDTO>> ObterProdutoSemelhante(@PathVariable String id, @PathVariable String quantia){
		
		List<Tags> tagsProdutoAlvo = produtoServico.findById(id).getTags();
		List<Produtos> todosOsProdutos = produtoServico.findAll()				;
		List<TagProduto> todasTagProdutos = new ArrayList<>();
		//temp nome variaveis
		for(Produtos produto : todosOsProdutos){
			
			TagProduto atualTagProduto = new TagProduto(produto, 0);
			
			for (Tags tag : produto.getTags()) {
				if(tagsProdutoAlvo.contains(tag))
					atualTagProduto.UpScore();
			}
			
			todasTagProdutos.add(atualTagProduto);
		}
		Collections.sort(todasTagProdutos, new ComparadorTagProduto());
		 
		List<Produtos> produtosSortidos = produtoServico.fromTagProduto(todasTagProdutos);
		
		List<produtoDTO> produtoDto = produtosSortidos
				.stream()
				.map(x -> new produtoDTO(x))
				.limit(Integer.parseInt(quantia))
				.collect(Collectors.toList());		
		
		return ResponseEntity.ok().body(produtoDto);
	}
	 
	@PostMapping("/cadastro")
	public ResponseEntity<Void> insert(@RequestBody produtoDTO objDto){
		Produtos produto = produtoServico.fromDTO(objDto);
		produto = produtoServico.insert(produto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/produtos/{id}")
				.buildAndExpand(produto.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		produtoServico.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Void> update(
			@RequestBody produtoDTO objDto, 
			@PathVariable String id){
		
		Produtos obj = produtoServico.fromDTO(objDto);
		obj.setId(id);
		obj = produtoServico.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/adicionar-tag/{id}")
	public ResponseEntity<Void> AddTag(
									@RequestBody Produtos objDto,
									@PathVariable String id){
		
		Produtos produto = produtoServico.findById(id);
		produto.getTags().addAll(objDto.getTags());
		produtoServico.insert(produto);
		
		for(int i = 0; i < objDto.getTags().size(); i++) {
			Tags tag = tagsServico.findById(objDto.getTags().get(i).getId());
			tag.getProdutos().add(produto);
			tagsServico.insert(tag);
		}
		
		return ResponseEntity.noContent().build();
	}
	 
	@GetMapping("/produtos-quantia/{quantia}")
	public ResponseEntity<List<produtoDTO>> ObterQuantiaDeProdutos(@PathVariable String quantia){
		  
		List<Produtos> produto = produtoServico.findAll();
		Collections.reverse(produto);
		List<produtoDTO> produtoDto = produto
									.stream()
									.map(x -> new produtoDTO(x))
									.limit(Integer.parseInt(quantia))
									.collect(Collectors.toList());
		return ResponseEntity.ok().body(produtoDto);
	}
	
}
