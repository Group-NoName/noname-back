package com.noname.uol.entidades;

import lombok.Data;

@Data
public class TagProduto implements Comparable<TagProduto>{

	private Produtos produto;
	
	private Integer score = 0;
	
	public TagProduto () {
	}
	public TagProduto (Produtos produto, Integer score) {
		this.produto = produto;
		this.score = score;
	}
	
	public void UpScore() {
		score++;
	}
	@Override
	public int compareTo(TagProduto o) {
		return this.score.compareTo(o.score);
	}
}
