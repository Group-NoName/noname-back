package com.noname.uol.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Document
public class Tags {
	
	@Id
	private String id;
	
	private String nome;

	@JsonIgnoreProperties(value = {"tags", "descricao"})
	@DBRef
	private List<Produtos> produtos = new ArrayList<>();

	public Tags() {}
	public Tags(String id, String nome, List<Produtos> produtos) {
		super();
		this.id = id;
		this.nome = nome;
		this.produtos = produtos;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tags other = (Tags) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}
