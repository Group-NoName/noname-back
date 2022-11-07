package com.noname.uol.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Document
public class Ofertas implements Serializable, InformacaoErro{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String nome;
	
	private Double preco;

	private Pacotes pacotes;
	

	
	public Ofertas() {}
	public Ofertas(String id, String nome, Double preco, Pacotes pacotes) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.pacotes = pacotes;

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ofertas other = (Ofertas) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public String obterNome() {
		return getNome();
	}
	
}
