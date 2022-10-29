package com.noname.uol.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noname.uol.entidades.Categorias;
import com.noname.uol.entidades.Pacotes;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.entidades.Servicos;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.ServicosServicos;

import io.swagger.annotations.Api;

@RequestMapping("/servico")
@RestController
@Api("servico")
public class ServicoControles {

	@Autowired
	private ServicosServicos servicosService;
	@Autowired
	private ProdutoServico servicosProdutos;
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> insertServico(@RequestBody Servicos obj){
		servicosService.insert(obj);
		return new ResponseEntity<>("Serviço Cadastrado com sucesso", HttpStatus.OK);
	}
	@GetMapping("/servicos")
	public ResponseEntity<?> getAllServicos(){
		List<Servicos> servicos = servicosService.findAll();
		return new ResponseEntity<>(servicos, HttpStatus.OK);
	}
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> putServicosName(@PathVariable String id, @RequestBody Servicos obj){
		List<Produtos> produtos = new ArrayList<>();
		produtos.addAll(servicosService.findById(id).getProdutos());
		obj.setId(id);
		obj.setProdutos(produtos);
		obj = servicosService.update(obj);
		return new ResponseEntity<>("Nome do servico alterado com sucesso", HttpStatus.OK);
	}
	@PutMapping("/atualizar-produtos/{id}")
	public ResponseEntity<?> putServicosProdutos(@PathVariable String id, @RequestBody Servicos obj){
		List<Produtos> produtos = new ArrayList<>();
		produtos.addAll(servicosService.findById(id).getProdutos());
		produtos.addAll(obj.getProdutos());
		obj.setId(id);
		obj.setProdutos(produtos);
		obj = servicosService.updateProdutos(obj);
		return new ResponseEntity<>("Produtos inserido com sucesso", HttpStatus.OK);
	}
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarTag(@PathVariable String id){
		servicosService.delete(id);
		return new ResponseEntity<>("Servico excluida com sucesso", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/servicos/{id}")
	public ResponseEntity<?> findService(@PathVariable String id){
		Servicos find = servicosService.findById(id);
		return new ResponseEntity<>(find, HttpStatus.OK);
	}
	@DeleteMapping("/deletar-produtos/{id}")
	public ResponseEntity<?> deletarProduto(@PathVariable String id, @RequestBody Servicos obj ){
		Servicos servicos = servicosService.findById(id);
		List<String> listaIds = new ArrayList<>();
		for (Produtos produto : obj.getProdutos()) 
			listaIds.add(produto.getId());
		List<Produtos> produtos = servicosProdutos.fromListIds(listaIds);
		servicos.getProdutos().removeAll(produtos);
		servicosService.insert(servicos);
		return new ResponseEntity<>("Produtos removidos da categoria com sucesso", HttpStatus.ACCEPTED);
	}
}
