package com.noname.uol.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.noname.uol.entidades.Images;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.entidades.Tags;

import lombok.Data;

@Data
public class produtoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	//private String descricao;
	//private Double preco;
	//private List<Images> images = new ArrayList<>();
	//private List<Tags> tags = new ArrayList<>();
	//private Double desconto;
	
	public produtoDTO() {
		
	}
	

	public produtoDTO(Produtos obj) {
		id = obj.getId();
		nome = obj.getNome();
		//descricao = obj.getDescricao();
		//preco = obj.getPreco();
		//images = obj.getImages();
		//tags = obj.getTags();
		//desconto = obj.getDesconto();
	}

}
