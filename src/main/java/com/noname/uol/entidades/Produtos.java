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
public class Produtos implements Serializable, InformacaoErro {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String nome;
	
	private String descricao;
		
	public Produtos() {}
	
	public Produtos(String id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produtos other = (Produtos) obj;
		return Objects.equals(id, other.id);
	}
	/*
	public void AddToTagList(Tags tag) {
		tags.add(tag);
	}*/

	@Override
	public String obterNome() {
		return this.getNome();
	}
	
}
