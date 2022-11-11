package com.noname.uol.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Ofertas;
import com.noname.uol.entidades.Pacotes;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.repositorios.OfertasRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class OfertasServico {

	@Autowired
	private OfertasRepositorio repositorio;
	@Autowired
	private ProdutoServico produtoServico;
	
	public List<Ofertas> findAll(){
		return repositorio.findAll();
	}
	public Ofertas insert(Ofertas obj) {
		return repositorio.save(obj);
	}
	public Ofertas findById(String id) {
		Optional<Ofertas> obj =  repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Oferta não encontrado"));
	}
	public void save(Ofertas oferta) {
		repositorio.save(oferta);
	}
	
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
	
	public Ofertas update(Ofertas obj) {
		Ofertas newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	public void updateData(Ofertas newObj, Ofertas obj) {
		newObj.setNome(obj.getNome());
		newObj.setPacotesOpcionais(obj.getPacotesOpcionais());
		newObj.setPacotesObrigatorios(obj.getPacotesObrigatorios());
		newObj.setPreco(obj.getPreco());
	}
	
	/*public List<Produtos> atualizarPrecosDescontos(double desconto, List<String> produtosParaAtualizar){
		List<Produtos> produtos = new ArrayList<>();
		for(String idProduto : produtosParaAtualizar) {
			
			Produtos produto = produtoServico.findById(idProduto);
			Double resultadoFinal = produto.getPreco() * desconto;
			produto.setDesconto(resultadoFinal);
			produtos.add(produto);
			produtoServico.insert(produto);
		}
		return produtos;
	} */
	
	/* public List<Produtos> atualizarApenasDescontos(double desconto, List<String> produtosParaAtualizar){
		List<Produtos> produtos = new ArrayList<>();
		for(String idProduto : produtosParaAtualizar) {
			
			Produtos produto = produtoServico.findById(idProduto);
			Double resultadoFinal = desconto;
			produto.setDesconto(resultadoFinal);
			produtos.add(produto);
			produtoServico.insert(produto);
		}
		return produtos;
	} */
	
	
}
