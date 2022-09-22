package com.noname.uol.entidades;

import java.util.Comparator;


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
		System.out.println("compareTo: " + o);
		return Comparators.SCORE.compare(this, o);
	}
	public static class Comparators{
	
		public static final Comparator<TagProduto> SCORE = (TagProduto o1, TagProduto o2) -> Integer.compare(o2.getScore(), o1.getScore());
		
	}
}
