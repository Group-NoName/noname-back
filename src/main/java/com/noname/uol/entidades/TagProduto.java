package com.noname.uol.entidades;

import lombok.Data;

@Data
public class TagProduto{

	private Produtos produto;
	
	private Integer score;
	
	public TagProduto () {
	}
	public TagProduto (Produtos produto, Integer score) {
		this.produto = produto;
		this.score = score;
	}
	
	public void UpScore() {
		score++;
	}
}
