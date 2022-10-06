package com.noname.uol.controles;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.noname.uol.entidades.Ofertas;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.OfertasServico;
import com.noname.uol.servicos.ProdutoServico;

@CrossOrigin
@RestController
@RequestMapping("/oferta")
public class OfertaControle {
	
	@Autowired
	private OfertasServico ofertaServico;
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@GetMapping("/ofertas")
	public ResponseEntity<List<Ofertas>> obterOfertas(){
		List<Ofertas> oferta = ofertaServico.findAll();
		return ResponseEntity.ok().body(oferta);
	}
	
	@GetMapping("/ofertas/{id}")
	public ResponseEntity<Ofertas> obterOfertaPorId(@PathVariable String id){
		Ofertas oferta = ofertaServico.findById(id);
		return ResponseEntity.ok().body(oferta);
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirOferta(@RequestBody Ofertas oferta){
		Ofertas obj = ofertaServico.insert(oferta);
		
		List<String> listaIds = new ArrayList<>();
		
		for (Produtos produto : oferta.getProdutos()) 
			listaIds.add(produto.getId());
		
		ofertaServico.atualizarApenasDescontos(oferta.getDesconto(), listaIds);
	
		return new ResponseEntity<>("Oferta cadastrada!", HttpStatus.CREATED);
	}
	
	@PostMapping("inserir-produtos-oferta")
	public ResponseEntity<?> atualizarTodosDescontosProdutosOferta(@RequestBody Ofertas objDto){
		Ofertas oferta = objDto;
		
		Double desconto = 1 - (oferta.getDesconto()/100);

		List<String> listaIds = new ArrayList<>();
		
		//#TODO Otimização
		for (Produtos produto : objDto.getProdutos()) 
			listaIds.add(produto.getId());
		
		List<Produtos> produtos = ofertaServico.atualizarPrecosDescontos(desconto, listaIds);
		
		ofertaServico.save(oferta);
		
		return ResponseEntity.ok().body(desconto);
	}
	
	
	
	@PutMapping("adicionar-produto/{id}")
	public ResponseEntity<?> adicionarOfertaProduto(@PathVariable String id, @RequestBody Ofertas objDto){
		Ofertas oferta = ofertaServico.findById(id);

		Double desconto = 1 - (oferta.getDesconto()/100);;
		
		List<String> listaIds = new ArrayList<>();
		
		boolean hasCopy = false;
		String errorLog = "";
		
		for (Produtos produto : objDto.getProdutos()) 
		{
			if(oferta.getProdutos().contains(produto)) {
				hasCopy = true;
				errorLog += "Produto '" + produtoServico.findById(produto.getId()).getNome() + "' já está cadastrado na oferta \n";
			}
			
			listaIds.add(produto.getId());
		}
		
		if(hasCopy) 
		{
			return new ResponseEntity<>(errorLog, HttpStatus.NOT_ACCEPTABLE);
		}
		else 
		{
			List<Produtos> produtos = ofertaServico.atualizarPrecosDescontos(desconto, listaIds);
			oferta.getProdutos().addAll(produtos);
			
			ofertaServico.save(oferta);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("remover-produto/{id}")
	public ResponseEntity<?> removerOfertaProduto(@PathVariable String id,@RequestBody Ofertas objDto){
	
		Ofertas oferta = ofertaServico.findById(id);

		Double desconto = 0.0;
		
		List<String> listaIds = new ArrayList<>();
		
		//#TODO Otimização
		for (Produtos produto : objDto.getProdutos()) 
			listaIds.add(produto.getId());
		
		List<Produtos> produtos = ofertaServico.atualizarPrecosDescontos(desconto, listaIds);
		
		oferta.getProdutos().removeAll(produtos);
		
		ofertaServico.save(oferta);

		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("retirar-todos-produtos/{id}")
	public ResponseEntity<?> retirarTodosProdutosDaOferta(@PathVariable String id){
		
		Ofertas oferta = ofertaServico.findById(id);
		
		List<String> listaIds = new ArrayList<>();
		
		//#TODO Otimização
		for (Produtos produto : produtoServico.findAll()) 
			listaIds.add(produto.getId());
		
		List<Produtos> produtos = ofertaServico.atualizarApenasDescontos(0.0, listaIds);
		
		oferta.getProdutos().removeAll(produtos);
		
		ofertaServico.save(oferta);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	
	//#TODO Deletar oferta
	@DeleteMapping("excluir/{id}")
	public ResponseEntity<Void> deletarOferta(@PathVariable String id){

		Ofertas oferta = ofertaServico.findById(id);
		
		List<String> listaIds = new ArrayList<>();
		
		//#TODO Otimização
		for (Produtos produto : oferta.getProdutos()) 
			listaIds.add(produto.getId());
			
		ofertaServico.atualizarPrecosDescontos(0, listaIds);
		
		ofertaServico.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
