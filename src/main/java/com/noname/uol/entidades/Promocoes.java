package com.noname.uol.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Promocoes implements Serializable, InformacaoErro{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String nome;
	
	@DBRef(lazy = true)
	private List<Ofertas> ofertasObrigatorias = new ArrayList<>();
	
	@DBRef(lazy = true)
	private List<Ofertas> ofertasOpcionais = new ArrayList<>();
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public String obterNome() {
		return getNome();
	}
	
}
