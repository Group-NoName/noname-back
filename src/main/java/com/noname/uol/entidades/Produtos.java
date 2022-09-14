package com.noname.uol.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Produtos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String nome;

	private String descricao;

	private Double preco;
	
	private List<Images> images;
	
	@DBRef(lazy = true)
	private List<Tags> tags = new ArrayList<>();
	
	public Produtos() {}
	
	public Produtos(String id, String nome, String descricao, List<Images> images, List<Tags> tags, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.images = images;
		this.tags = tags;
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
	
	public void AddToTagList(Tags tag) {
		tags.add(tag);
	}
	
}
