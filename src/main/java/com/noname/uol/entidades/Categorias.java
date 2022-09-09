package com.noname.uol.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
public class Categorias implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String nome;
	
	@DBRef(lazy = true)
	private List<Produtos> produtos = new ArrayList<>();
	
	public Categorias() {
	}

	public Categorias(String id, String nome, List<Produtos> produtos) {
		super();
		this.id = id;
		this.nome = nome;
		this.produtos = produtos;
	}
	
	public List<Produtos>  getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produtos>  produtos) {
		this.produtos = produtos;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
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
		Categorias other = (Categorias) obj;
		return Objects.equals(id, other.id);
	}

}
