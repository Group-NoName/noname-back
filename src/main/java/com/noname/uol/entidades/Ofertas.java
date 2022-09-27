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
public class Ofertas implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private Double desconto;
	
	@DBRef(lazy = true)
	private List<Produtos> produtos = new ArrayList<>();
	
	public Ofertas() {}
	public Ofertas(String id, Double desconto, List<Produtos> produtos) {
		super();
		this.id = id;
		this.desconto = desconto;
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
		Ofertas other = (Ofertas) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}
