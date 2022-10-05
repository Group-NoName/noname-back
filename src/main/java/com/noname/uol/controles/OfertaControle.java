package com.noname.uol.controles;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirOferta(@RequestBody Ofertas oferta){
		Ofertas obj = ofertaServico.insert(oferta);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("inserir")
	public ResponseEntity<?> atualizarTodosDescontosProdutosOferta(@RequestBody Ofertas objDto){
		Ofertas oferta = objDto;
		
		Double desconto = oferta.getDesconto()/100;

		List<String> listaIds = new ArrayList<>();
		
		//#TODO Otimização
		for (Produtos produto : objDto.getProdutos()) 
			listaIds.add(produto.getId());
		
		List<Produtos> produtos = ofertaServico.atualizarDescontos(desconto, listaIds);
		
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
		
		List<Produtos> produtos = ofertaServico.atualizarDescontos(desconto, listaIds);
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
		
		List<Produtos> produtos = ofertaServico.atualizarDescontos(desconto, listaIds);
		
		oferta.getProdutos().removeAll(produtos);
		
		ofertaServico.save(oferta);

		return ResponseEntity.noContent().build();
	}
	
	//#TODO Deletar oferta
}
