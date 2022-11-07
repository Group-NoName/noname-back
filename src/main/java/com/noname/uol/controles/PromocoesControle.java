package com.noname.uol.controles;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.noname.uol.entidades.Promocoes;
import com.noname.uol.servicos.PromocoesServico;

import io.swagger.annotations.Api;

@CrossOrigin
@RestController
@RequestMapping("/promocao")
@Api(value="promocao")
public class PromocoesControle {
	
	@Autowired
	private PromocoesServico promocoesServico;
	
	@GetMapping("/promocoes")
	public ResponseEntity<List<Promocoes>> obterPromocoes(){
		List<Promocoes> promocao = promocoesServico.findAll();
		return new ResponseEntity<>(promocao, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/promocoes/{id}")
	public ResponseEntity<Promocoes> obterPromocaoPorId(@PathVariable String id){
		Promocoes promocao = promocoesServico.findById(id);
		return new ResponseEntity<>(promocao, HttpStatus.ACCEPTED);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastarPromocao(@RequestBody Promocoes promocao){
		
		promocoesServico.insert(promocao);
		return new ResponseEntity<>("Promoção cadastrada com sucesso", HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarPromocao(@PathVariable String id, @RequestBody Promocoes promocao){
		promocao.setId(id);
		promocoesServico.update(promocao);
		return new ResponseEntity<>("Promoção atualizada com sucesso", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarPromocao(@PathVariable String id){
		promocoesServico.delete(id);
		return new ResponseEntity<>("Promoção excluída com sucesso", HttpStatus.ACCEPTED);
	}	
	


	
}
