package com.noname.uol.entidades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Servicos {

	private String id;
	private String nome;
	@DBRef(lazy = true)
	private List<Produtos> produtos = new ArrayList<Produtos>();
}
