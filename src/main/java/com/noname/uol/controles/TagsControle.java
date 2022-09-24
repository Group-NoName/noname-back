package com.noname.uol.controles;

import java.util.List;
import java.lang.Void;
import java.net.URI;

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
import com.noname.uol.entidades.Tags;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.TagsServico;

@CrossOrigin
@RestController
@RequestMapping("/tag")
public class TagsControle {

	@Autowired
	private TagsServico tagServico;
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@GetMapping("/tags")
	public ResponseEntity<List<Tags>> obterTags(){
		List<Tags> tags = tagServico.findAll();
		return ResponseEntity.ok().body(tags);
	}
	
	@GetMapping("/tags/{id}")
	public ResponseEntity<Tags> obterTagId(@PathVariable String id){
		Tags tag = tagServico.findById(id);
		return ResponseEntity.ok().body(tag);
	}
	
	@PostMapping("/inserir")
	public ResponseEntity<Void> inserirNovaTag(@RequestBody Tags tag){
		Tags obj = tagServico.insert(tag);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> deletarTag(@PathVariable String id){
		tagServico.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/tag-produtos/{tagId}/{produtoId}")
	public ResponseEntity<Void> deleteRelacao(
											@PathVariable String tagId,
											@PathVariable String produtoId){
		
		Produtos produto = produtoServico.findById(produtoId);
		Tags tag = tagServico.findById(tagId);
		produto.getTags().remove(tag);
		produtoServico.save(produto);
		return ResponseEntity.noContent().build();
	}
	
	
}
