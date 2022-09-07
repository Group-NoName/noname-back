package com.noname.uol.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.noname.uol.entidades.Images;
import com.noname.uol.entidades.Produtos;

public class produtoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	private String descricao;
	private Double preco;
	private List<Images> images = new ArrayList<>();
	
	public produtoDTO() {
		
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public List<Images> getImages() {
		return images;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}

	public produtoDTO(Produtos obj) {
		id = obj.getId();
		nome = obj.getNome();
		descricao = obj.getDescricao();
		preco = obj.getPreco();
		images = obj.getImages();
	}
}
