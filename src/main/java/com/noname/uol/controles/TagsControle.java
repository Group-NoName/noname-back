package com.noname.uol.controles;

import java.util.ArrayList;
import java.util.List;
import java.lang.Void;
import java.net.URI;

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
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.TagsServico;
import com.noname.uol.servicos.excecao.TratamentoErro;

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
		return new ResponseEntity<>(tags, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/tags/{id}")
	public ResponseEntity<Tags> obterTagId(@PathVariable String id){
		Tags tag = tagServico.findById(id);
		return new ResponseEntity<>(tag, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirNovaTag(@RequestBody Tags tag){
		
//		for (Tags tagObj: tagServico.findAll()) {
//			if(tagObj.getNome().equals(tag.getNome()))
//		}
		List<Tags> targetList = new ArrayList<>();
		targetList.add(tag);
		TratamentoErro<Tags> te = new TratamentoErro<Tags>();
		te.verificarCopiaItemUnico(tagServico.findAll(), tag);
		if(te.getHasError()) 
			return new ResponseEntity<>("Nome de tag não pode ser repetido", HttpStatus.CONFLICT);
		tagServico.insert(tag);

		return new ResponseEntity<>("Tag cadastrada com sucesso", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarTag(@PathVariable String id, @RequestBody Tags tag){
		
		for (Tags tagObj: tagServico.findAll()) {
			if(tagObj.getNome().equals(tag.getNome()))
				return new ResponseEntity<>("Nome de tag não pode ser repetido", HttpStatus.CONFLICT);
		}
		
		List<Produtos> produtos = new ArrayList<>();
		produtos.addAll(tagServico.findById(id).getProdutos());
		
		tag.setId(id);
		tag.setProdutos(produtos);
		tag = tagServico.update(tag);
		return new ResponseEntity<>("Tag atualizada com sucesso", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletarTag(@PathVariable String id){
		tagServico.delete(id);
		return new ResponseEntity<>("Tag excluida com sucesso", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/tag-produtos/{tagId}/{produtoId}")
	public ResponseEntity<?> deleteRelacao(
											@PathVariable String tagId,
											@PathVariable String produtoId){
		
		Produtos produto = produtoServico.findById(produtoId);
		Tags tag = tagServico.findById(tagId);
		produto.getTags().remove(tag);
		produtoServico.save(produto);
		tag.getProdutos().remove(produto);
		tagServico.insert(tag);
		return new ResponseEntity<>("Tag excluida do produto com sucesso", HttpStatus.ACCEPTED);
	}
	@PutMapping("/atualizar/{tagId}")
	public ResponseEntity<Void> atualizar(@PathVariable String tagId, @RequestBody Tags body){
		Tags tag = tagServico.body(body);
		tag.setId(tagId);
		tag = tagServico.update(tag);
		return ResponseEntity.noContent().build();	
		}
	
}
