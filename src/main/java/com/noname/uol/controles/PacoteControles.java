package com.noname.uol.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noname.uol.entidades.Pacotes;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.servicos.PacoteServico;

@CrossOrigin
@RestController
@RequestMapping("/pacote")
public class PacoteControles {

	@Autowired
	private PacoteServico pacoteServico;
	
	@GetMapping("/pacotes")
	public ResponseEntity<List<Pacotes>> obterPacotes(){
		List<Pacotes> pacote = pacoteServico.obterPacotes();
		return ResponseEntity.ok().body(pacote);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> insetirPacote(@RequestBody Pacotes objDto){
		Pacotes pacote = objDto;
		pacoteServico.save(pacote);
		return ResponseEntity.noContent().build();
	}
}
