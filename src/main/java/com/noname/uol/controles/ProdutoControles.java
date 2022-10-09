package com.noname.uol.controles;

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


import com.noname.uol.dto.produtoDTO;
import com.noname.uol.entidades.TagProduto;
import com.noname.uol.entidades.Tags;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.TagsServico;


import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
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
		return new ResponseEntity<>(produtoDto, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<produtoDTO> obterProdutoId(@PathVariable String id) {
		Produtos produto = produtoServico.findById(id);
		return new ResponseEntity<>(new produtoDTO(produto), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/produtos-semelhantes/{id}/{quantia}")
	public ResponseEntity<List<produtoDTO>> obterProdutosSemelhantes(@PathVariable String id, @PathVariable String quantia){

		List<Tags> tagsProdutoAlvo = produtoServico.findById(id).getTags();
		List<Produtos> todosOsProdutos = produtoServico.findAll();
		List<TagProduto> todasTagProdutos = new ArrayList<>();
		
		for(Produtos produto : todosOsProdutos){
			if (produto == produtoServico.findById(id))
				continue;
			TagProduto atualTagProduto = new TagProduto(produto, 0);
			
			for (Tags tag : produto.getTags()) {
				if(tagsProdutoAlvo.contains(tag)) {
					atualTagProduto.UpScore();
				}
			}
			todasTagProdutos.add(atualTagProduto);
		}


		Collections.sort(todasTagProdutos, TagProduto.Comparators.SCORE);
		
		List<Produtos> produtosSortidos = produtoServico.fromTagProduto(todasTagProdutos);
		
		
		
		List<produtoDTO> produtoDto = produtosSortidos
				.stream()
				.map(x -> new produtoDTO(x))
				.limit(Integer.parseInt(quantia))
				.collect(Collectors.toList());		
		
		return new ResponseEntity<>(produtoDto, HttpStatus.ACCEPTED);
	}
	 
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirProduto(@RequestBody produtoDTO objDto){
		Produtos produto = produtoServico.fromDTO(objDto);
		
		for (Produtos produtoObj : produtoServico.findAll()) {
			if(produto.getNome().equals(produtoObj.getNome())) {
				return new ResponseEntity<>("Produto não pode ter nome repetido", HttpStatus.CONFLICT);
			}
		}
		
		
		produto = produtoServico.insert(produto);
		return new ResponseEntity<>("Produto cadastrado com sucesso!", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletarProduto(@PathVariable String id) {
		produtoServico.delete(id);
		return new ResponseEntity<>("Produto deletado com sucesso!", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarProduto(
			@RequestBody produtoDTO objDto, 
			@PathVariable String id){
		
		Produtos obj = produtoServico.fromDTO(objDto);
		obj.setId(id);
		obj = produtoServico.update(obj);
		return new ResponseEntity<>("Produto atualizado com sucesso!", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/adicionar-tag/{id}")
	public ResponseEntity<?> adicionarTag(
			@RequestBody Produtos objDto, 
			@PathVariable String id){
		
		Produtos produto = produtoServico.findById(id);
		boolean hasCopy = false;
		String errorLog = "";
		
		for (Tags tag : objDto.getTags()) 
			if(produto.getTags().contains(tag)) {
				hasCopy = true;
				errorLog += tagsServico.findById(tag.getId()).getNome();
			}
		
		if(hasCopy) {
			return new ResponseEntity<>(errorLog, HttpStatus.NOT_ACCEPTABLE);
		}else {
			produto.getTags().addAll(objDto.getTags());
			produtoServico.insert(produto);
		}
		return new ResponseEntity<>("Tag adicionada com sucesso", HttpStatus.ACCEPTED);

	}
	 
	@GetMapping("/produtos-quantia/{quantia}")
	public ResponseEntity<List<produtoDTO>> obterQuantiaDeProdutos(@PathVariable String quantia){
		  
		List<Produtos> produto = produtoServico.findAll();
		Collections.reverse(produto);
		List<produtoDTO> produtoDto = produto
									.stream()
									.map(x -> new produtoDTO(x))
									.limit(Integer.parseInt(quantia))
									.collect(Collectors.toList());
		return new ResponseEntity<>(produtoDto, HttpStatus.FOUND);
	}
	
}
