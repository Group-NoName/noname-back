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
public class Pacotes implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String nome;
	
	private String descricao;
	
	private Double preco;
	
	private List<Images> images = new ArrayList<>();
	
	@JsonIgnoreProperties(value = {"tags", "descricao", "images"})
	@DBRef(lazy = true)
	private List<Produtos> produtos = new ArrayList<>();	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pacotes other = (Pacotes) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Pacotes() {}
	
	public Pacotes(String id, String nome, String descricao, Double preco, List<Images> images,
			List<Produtos> produtos) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.images = images;
		this.produtos = produtos;
	}
	
}
