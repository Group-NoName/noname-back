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
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.TagsServico;

import java.net.URI;
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
	public ResponseEntity<Void> update(@RequestBody produtoDTO objDto, @PathVariable String id){
		Produtos obj = produtoServico.fromDTO(objDto);
		obj.setId(id);
		obj = produtoServico.update(obj);
		return ResponseEntity.noContent().build();
	}
}
