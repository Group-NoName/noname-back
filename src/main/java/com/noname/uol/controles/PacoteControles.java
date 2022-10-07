package com.noname.uol.controles;

import java.util.ArrayList;
import java.util.List;

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

import com.noname.uol.entidades.Pacotes;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.PacoteServico;
import com.noname.uol.servicos.ProdutoServico;
@CrossOrigin
@RestController
@RequestMapping("/pacote")
public class PacoteControles {

	@Autowired
	private PacoteServico pacoteServico;
	
	@Autowired
	private ProdutoServico produtoServico;
	
	
	
	@GetMapping("/pacotes")
	public ResponseEntity<List<Pacotes>> obterPacotes(){
		List<Pacotes> pacotes = pacoteServico.findAll();
		return new ResponseEntity<>(pacotes, HttpStatus.ACCEPTED);
	}

	@GetMapping("/pacote/{id}")
	public ResponseEntity<Pacotes> obterPacoteId(@PathVariable String id){
		Pacotes pacote = pacoteServico.findById(id);
		return new ResponseEntity<>(pacote, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirPacote(@RequestBody Pacotes objDto){
		Pacotes pacote = objDto;
		pacoteServico.save(pacote);
		return new ResponseEntity<>("Pacote criado com sucesso", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletarPacote(@PathVariable String id){
		pacoteServico.delete(id);
		return new ResponseEntity<>("Pacote apagado com sucesso", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("atualizar/{id}")
	public ResponseEntity<?> atualizarPacote(
			@RequestBody Pacotes objDto,
			@PathVariable String id){
		
		Pacotes obj = objDto;
		obj.setId(id);
		obj = pacoteServico.update(obj);
		return new ResponseEntity<>("Pacote atualizado com sucesso", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("inserir-produto/{id}")
	public ResponseEntity<?> inserirProdutosNoPacote(
			@PathVariable String id,
			@RequestBody Pacotes objDto){
		
		Pacotes pacote = pacoteServico.findById(id);
		
		List<String> listaIds = new ArrayList<>();
		
		boolean hasCopy = false;
		String errorLog = "";
		for (Produtos produto : objDto.getProdutos()) {
			if(pacote.getProdutos().contains(produto)) {
				hasCopy = true;
				errorLog += produtoServico.findById(produto.getId()).getNome();
			}
			listaIds.add(produto.getId());
		
		}
		if(hasCopy)
			return new ResponseEntity<>(errorLog, HttpStatus.NOT_ACCEPTABLE);
		List<Produtos> produtos = produtoServico.fromListIds(listaIds);
		
		pacote.getProdutos().addAll(produtos);
	
		pacoteServico.save(pacote);
		
		return new ResponseEntity<>("Produtos inseridos com sucesso no pacote", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("remover-produto/{id}")
	public ResponseEntity<?> removerProdutosNoPacote(
			@PathVariable String id,
			@RequestBody Pacotes objDto){
		
		Pacotes pacote = pacoteServico.findById(id);
		
		List<String> listaIds = new ArrayList<>();
		
		for (Produtos produto : objDto.getProdutos()) 
			listaIds.add(produto.getId());
		

		
		List<Produtos> produtos = produtoServico.fromListIds(listaIds);
		
		pacote.getProdutos().removeAll(produtos);
		
		pacoteServico.save(pacote);
		
		return new ResponseEntity<>("Produtos removidos do pacote com sucesso", HttpStatus.ACCEPTED);	
	}
}
