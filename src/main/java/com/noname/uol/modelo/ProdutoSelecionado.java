package com.noname.uol.modelo;

import com.noname.uol.entidades.Produtos;

import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class ProdutoSelecionado {
	public Produtos selecionar(List<Produtos> produtos, String id) {
		Produtos selecionado = null;
		for (Produtos produto : produtos) {
			if(produto.getId() == id) {
				selecionado = produto;
			}
		}
		return selecionado;
	}
}
