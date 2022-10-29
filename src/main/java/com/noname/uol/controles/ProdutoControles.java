package com.noname.uol.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import com.noname.uol.dto.produtoDTO;
import com.noname.uol.entidades.TagProduto;
import com.noname.uol.entidades.Tags;
import com.noname.uol.repositorios.produtosRepositorio;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.TagsServico;
import com.noname.uol.servicos.excecao.TratamentoErro;

import io.swagger.annotations.Api;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/produto")
@Api(value="produto")
public class ProdutoControles {
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@Autowired	
	private TagsServico tagsServico;
	
	@Autowired
	private produtosRepositorio repositorio;
	
	@Autowired
	private TratamentoErro tratamentoErro;
	
	@GetMapping("/produtosPage")
	public ResponseEntity<?> obterPageProdutos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){
		
		List<Produtos> lista = new ArrayList<Produtos>();
		
		Pageable paging = PageRequest.of(page, size);
		
		Page<Produtos> pageProd = repositorio.findAll(paging);
		
		lista = pageProd.getContent();
		
		Map<String, Object> response = new HashMap<>();
		response.put("Produtos", lista);
		response.put("Pagina atual", pageProd.getNumber());
		response.put("Total de itens", pageProd.getTotalElements());
		response.put("Total de paginas", pageProd.getTotalPages());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
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
	
	@GetMapping("/produtos-quantia/{quantia}")
	public ResponseEntity<List<produtoDTO>> obterQuantiaDeProdutos(@PathVariable String quantia){
		  
		List<Produtos> produto = produtoServico.findAll();
		Collections.reverse(produto);
		List<produtoDTO> produtoDto = produto
									.stream()
							.map(x -> new produtoDTO(x))
									.limit(Integer.parseInt(quantia))
									.collect(Collectors.toList());
		return new ResponseEntity<>(produtoDto, HttpStatus.ACCEPTED);
	}
	
	/*
	@PutMapping("/adicionar-tag/{id}")
	public ResponseEntity<?> adicionarTag(
			@RequestBody Produtos objDto, 
			@PathVariable String id){
		
		Produtos produto = produtoServico.findById(id);
		
		TratamentoErro<Tags> tratamentoErros = new TratamentoErro<Tags>();
		
		tratamentoErros.verificarCopiaEntreListas(objDto.getTags(), produto.getTags());

		if(tratamentoErros.getHasError()) {
			return new ResponseEntity<>(tratamentoErros.getErrorLog(), HttpStatus.NOT_ACCEPTABLE);
		}else {
			produto.getTags().addAll(objDto.getTags());
			produtoServico.insert(produto);
		}
		return new ResponseEntity<>("Tag adicionada com sucesso", HttpStatus.ACCEPTED);

	}*/
	
	/*
	@GetMapping("/produtos-semelhantes/{id}/{quantia}")
	public ResponseEntity<List<produtoDTO>> obterProdutosSemelhantes(@PathVariable String id, @PathVariable String quantia){

		List<Produtos> todosOsProdutos = produtoServico.findAll();
		
		List<TagProduto> todasTagProdutos = tagsServico.filtrarTagProdutoSemelhante(todosOsProdutos, id);
		
		Collections.sort(todasTagProdutos, TagProduto.Comparators.SCORE);
		
		List<Produtos> produtosSortidos = produtoServico.fromTagProduto(todasTagProdutos);

		List<produtoDTO> produtoDto = produtosSortidos
				.stream()
				.map(x -> new produtoDTO(x))
				.limit(Integer.parseInt(quantia))
				.collect(Collectors.toList());		
		
		return new ResponseEntity<>(produtoDto, HttpStatus.ACCEPTED);
	}*/
}
