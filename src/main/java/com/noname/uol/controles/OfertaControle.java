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
		return new ResponseEntity<>(oferta, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/ofertas/{id}")
	public ResponseEntity<Ofertas> obterOfertaPorId(@PathVariable String id){
		Ofertas oferta = ofertaServico.findById(id);
		return new ResponseEntity<>(oferta, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> inserirOferta(@RequestBody Ofertas oferta){
		
		//#TODO organização
		
		Double desconto = 1 - (oferta.getDesconto()/100);
		
		List<String> listaIds = new ArrayList<>();
		
		boolean hasProduct = false;
		String errorLog = "";
		for (Produtos produto : oferta.getProdutos()) {
			if(produtoServico.hasDescount(produto.getId())) {
				hasProduct = true;
				errorLog += produtoServico.findById(produto.getId()).getNome() + " ";
			}
			listaIds.add(produto.getId());
		}
		if(hasProduct == true) {
			return new ResponseEntity<>(errorLog, HttpStatus.CONFLICT);
		}else {
			ofertaServico.atualizarPrecosDescontos(desconto, listaIds);
			Ofertas obj = ofertaServico.insert(oferta);
		
			return new ResponseEntity<>("Oferta cadastrada com sucesso", HttpStatus.CREATED);
		}	
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
		
		return new ResponseEntity<>("Descontos dos produtos atualizados com sucesso", HttpStatus.ACCEPTED);
	}
	
	
	
	@PutMapping("adicionar-produto/{id}")
	public ResponseEntity<?> adicionarOfertaProduto(@PathVariable String id, @RequestBody Ofertas objDto){
		Ofertas oferta = ofertaServico.findById(id);

		Double desconto = 1 - (oferta.getDesconto()/100);;
		
		List<String> listaIds = new ArrayList<>();
		
		boolean hasCopy = false;
		List<Produtos> errorLog = new ArrayList<>();
		
		for (Produtos produto : objDto.getProdutos()) 
		{
			if(oferta.getProdutos().contains(produto)) {
				hasCopy = true;
				errorLog.add(produtoServico.findById(produto.getId()));
			}else if(produtoServico.hasDescount(produto.getId())) 
				return new ResponseEntity<>(produtoServico.findById(produto.getId()), HttpStatus.CONFLICT);
				
			
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
		return new ResponseEntity<>("Produtos adicionados com sucesso", HttpStatus.ACCEPTED);
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

		return new ResponseEntity<>("Produtos removidos com sucesso", HttpStatus.ACCEPTED);
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
		
		return new ResponseEntity<>("Produtos retirados com sucesso", HttpStatus.ACCEPTED);
	}
	
	
	//#TODO Deletar oferta
	@DeleteMapping("excluir/{id}")
	public ResponseEntity<?> deletarOferta(@PathVariable String id){

		Ofertas oferta = ofertaServico.findById(id);

		List<String> listaIds = new ArrayList<>();
		
		//#TODO Otimização
		for (Produtos produto : oferta.getProdutos()) 
			if(produto != null)
				listaIds.add(produto.getId());
			
		ofertaServico.atualizarPrecosDescontos(0, listaIds);

		ofertaServico.delete(id);
		
		return new ResponseEntity<>("Oferta excluida com sucesso", HttpStatus.ACCEPTED);
	}
}
