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
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("inserir-produtos-oferta")
	public ResponseEntity<?> atualizarTodosDescontosProdutosOferta(@RequestBody Ofertas objDto){
		Ofertas oferta = objDto;
		
		Double desconto = oferta.getDesconto()/100;

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

		Double desconto = oferta.getDesconto()/100;
		
		List<String> listaIds = new ArrayList<>();
		
		//#TODO Otimização
		for (Produtos produto : objDto.getProdutos()) 
			listaIds.add(produto.getId());
		
		List<Produtos> produtos = ofertaServico.atualizarPrecosDescontos(desconto, listaIds);
		oferta.getProdutos().addAll(produtos);
		
		ofertaServico.save(oferta);

		return ResponseEntity.noContent().build();
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
