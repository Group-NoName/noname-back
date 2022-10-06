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
		return ResponseEntity.ok().body(pacotes);
	}

	@GetMapping("/pacote/{id}")
	public ResponseEntity<Pacotes> obterPacoteId(@PathVariable String id){
		Pacotes pacote = pacoteServico.findById(id);
		return ResponseEntity.ok().body(pacote);
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirPacote(@RequestBody Pacotes objDto){
		Pacotes pacote = objDto;
		pacoteServico.save(pacote);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> deletarPacote(@PathVariable String id){
		pacoteServico.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("atualizar/{id}")
	public ResponseEntity<Void> atualizarPacote(
			@RequestBody Pacotes objDto,
			@PathVariable String id){
		
		Pacotes obj = objDto;
		obj.setId(id);
		obj = pacoteServico.update(obj);
		return ResponseEntity.noContent().build();
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
				errorLog += "\n \n Produto de id: '" + produto.getId() + "'\ne nome: '" + produtoServico.findById(produto.getId()).getNome() + "' \n já está cadastrado no pacote";
			}
			listaIds.add(produto.getId());
		
		}
		if(hasCopy)
			return new ResponseEntity<>(errorLog, HttpStatus.NOT_ACCEPTABLE);
		List<Produtos> produtos = produtoServico.fromListIds(listaIds);
		
		pacote.getProdutos().addAll(produtos);
	
		pacoteServico.save(pacote);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
		
		return ResponseEntity.noContent().build();
	}
}
