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

import com.noname.uol.entidades.Servicos;
import com.noname.uol.entidades.Pacotes;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.entidades.Servicos;
import com.noname.uol.servicos.ProdutoServico;
import com.noname.uol.servicos.ServicosServicos;
import com.noname.uol.servicos.excecao.TratamentoErro;

import io.swagger.annotations.Api;

@RequestMapping("/servico")
@RestController
@Api("servico")
public class ServicoControles {

	@Autowired
	private ServicosServicos servicosService;
	@Autowired
	private ProdutoServico servicosProdutos;
	
	@GetMapping("/servicos/{id}")
	public ResponseEntity<?> ObterServicoPorId(@PathVariable String id){
		Servicos find = servicosService.findById(id);
		return new ResponseEntity<>(find, HttpStatus.OK);
	}
	

	@GetMapping("/servicos")
	
	public ResponseEntity<?> ObterTodosServicos(){
		List<Servicos> servicos = servicosService.findAll();
		return new ResponseEntity<>(servicos, HttpStatus.OK);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> updateServico(@PathVariable String id, @RequestBody Servicos obj){
		
		List<Produtos> produtos = new ArrayList<>();
		
		produtos.addAll(servicosService.findById(id).getProdutos());
		
		obj.setId(id);
		
		obj.setProdutos(produtos);
		
		obj = servicosService.update(obj);
		
		return new ResponseEntity<>("Serviço atualizado com sucesso", HttpStatus.OK);
	}
	


	
	@PostMapping("/cadastro")
	public ResponseEntity<?> insertServico(@RequestBody Servicos obj){
		

		servicosService.insert(obj);
		return new ResponseEntity<>("Serviço Cadastrado com sucesso", HttpStatus.OK);
	
	}	

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarServico(@PathVariable String id){
		servicosService.delete(id);
		return new ResponseEntity<>("Serviço excluido com sucesso", HttpStatus.ACCEPTED);
	}
	

	@PutMapping("/inserir-produto/{id}")
	public ResponseEntity<?> inserirProdutosNoServico(@PathVariable String id, @RequestBody Servicos obj){

		Servicos servico = servicosService.findById(id);
		List<String> listaIds = new ArrayList<>();
		
		for(Produtos produto : obj.getProdutos()) 
			listaIds.add(produto.getId());
		
		List<Produtos> produtos = servicosProdutos.fromListIds(listaIds);

		TratamentoErro<Produtos> tratamentoErro = new TratamentoErro<Produtos>();
		
		tratamentoErro.verificarCopiaEntreListas(produtos, servico.getProdutos());
		
		if(tratamentoErro.getHasError()) {
			return new ResponseEntity<>(tratamentoErro.getErrorLog(), HttpStatus.NOT_ACCEPTABLE);
		}
		else {
			
			
			servico.getProdutos().addAll(produtos);
		
			servicosService.insert(servico);
			
			return new ResponseEntity<>("Produtos inserido com sucesso", HttpStatus.OK);
		}
	}
	
	
	@PutMapping("/deletar-produto/{id}")
	public ResponseEntity<?> removerProdutosDoServico(@PathVariable String id, @RequestBody Servicos obj ){
		
		Servicos servico = servicosService.findById(id);
		
		List<String> listaIds = new ArrayList<>();
		
		for (Produtos produto : obj.getProdutos()) 
			listaIds.add(produto.getId());
		
		List<Produtos> produtos = servicosProdutos.fromListIds(listaIds);
		
		servico.getProdutos().removeAll(produtos);
		
		servicosService.insert(servico);
		
		return new ResponseEntity<>("Produtos removidos do pacote com sucesso", HttpStatus.ACCEPTED);
	}
}
