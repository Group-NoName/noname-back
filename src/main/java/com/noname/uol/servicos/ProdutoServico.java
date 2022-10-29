package com.noname.uol.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.dto.produtoDTO;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.entidades.TagProduto;
import com.noname.uol.repositorios.produtosRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class ProdutoServico {

	@Autowired
	private produtosRepositorio repositorio;
	
	public List<Produtos> findAll(){
		return repositorio.findAll();
	}
	
	public Produtos findById(String id) {
		Optional<Produtos> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
	}
	
	public Produtos insert(Produtos obj) {
		return repositorio.save(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
	
	public Produtos update(Produtos obj) {
		Produtos newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	
	private void updateData(Produtos newObj, Produtos obj) {
		newObj.setNome(obj.getNome());
	}
	
	public Produtos fromDTO(produtoDTO objDto) {
		return new Produtos(
				objDto.getId(),
				objDto.getNome()
				);
	}
	
	public List<Produtos> fromTagProduto(List<TagProduto> listTagProduto) {
		List<Produtos> obj = listTagProduto.stream()
								.map(TagProduto -> TagProduto.getProduto())
								.collect(Collectors.toList());
		return obj;
	}
	
	public List<Produtos> fromListIds(List<String> listId){
		List<Produtos> obj = new ArrayList<>();
		for (String string : listId)
			obj.add(findById(string));
		
		return obj;

	}
	
	public void save(Produtos produto) {
		repositorio.save(produto);
	}
	
	/*
	public boolean hasDescount(String productId) {
		if(findById(productId).getDesconto() != 0) return true;
		
		return false;
	}*/
	
	

	/*
	public void resetar(String id, Double number) {
		findById(id).setDesconto(number);;	
	}*/
	
}
