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
import com.noname.uol.entidades.Tags;
import com.noname.uol.servicos.TagsServico;

@CrossOrigin
@RestController
@RequestMapping("/tags")
public class TagsControle {

	@Autowired
	private TagsServico service;
	
	@GetMapping("/tags")
	public ResponseEntity<List<Tags>> getAllTags(){
		List<Tags> tags = service.findAll();
		return ResponseEntity.ok().body(tags);
	}
	
	@GetMapping("/tags/{id}")
	public ResponseEntity<Tags> GetTagById(@PathVariable String id){
		Tags tag = service.findById(id);
		return ResponseEntity.ok().body(tag);
	}
	
	@PostMapping("/inserir")
	public ResponseEntity<Void> InsertNewTag(@RequestBody Tags tag){
		Tags obj = service.insert(tag);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> DeleteTag(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
