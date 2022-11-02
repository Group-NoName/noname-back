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

import com.noname.uol.entidades.Ofertas;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.OfertasServico;
import com.noname.uol.servicos.ProdutoServico;

import io.swagger.annotations.Api;

@CrossOrigin
@RestController
@RequestMapping("/oferta")
@Api(value="oferta")
public class OfertaControle {
	
	@Autowired
	private OfertasServico ofertaServico;
	
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
	public ResponseEntity<?> cadastrarOferta(@RequestBody Ofertas ofertaDto){
		
		ofertaServico.insert(ofertaDto);
		return new ResponseEntity<>("Oferta cadastrada com sucesso", HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarOferta(@PathVariable String id, @RequestBody Ofertas ofertaDto){
		ofertaDto.setId(id);
		ofertaServico.update(ofertaDto);
		return new ResponseEntity<>("Oferta atualizada com sucesso", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarOferta(@PathVariable String id){
		ofertaServico.delete(id);
		return new ResponseEntity<>("Oferta excluída com sucesso", HttpStatus.ACCEPTED);
	}	
	
}
