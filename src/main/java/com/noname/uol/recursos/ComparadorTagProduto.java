package com.noname.uol.recursos;

import java.util.Comparator;
import java.util.List;

import com.noname.uol.entidades.TagProduto;

public class ComparadorTagProduto implements Comparator<TagProduto>{

	@Override
	public int compare(TagProduto o1, TagProduto o2) {
		return o2.getScore() - o1.getScore();
	}
}
