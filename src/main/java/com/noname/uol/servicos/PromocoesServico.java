package com.noname.uol.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.dto.produtoDTO;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.entidades.Promocoes;
import com.noname.uol.entidades.TagProduto;
import com.noname.uol.repositorios.PromocoesRepositorio;
import com.noname.uol.repositorios.produtosRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class PromocoesServico {
	
	@Autowired
	private PromocoesRepositorio repositorio;
	
	public List<Promocoes> findAll(){
		return repositorio.findAll();
	}
	
	public Promocoes findById(String id) {
		Optional<Promocoes> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
	}
	
	public Promocoes insert(Promocoes obj) {
		return repositorio.save(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
	
	public Promocoes update(Promocoes obj) {
		Promocoes newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	
	private void updateData(Promocoes newObj, Promocoes obj) {
		newObj.setNome(obj.getNome());
	}
	

	public void save(Promocoes promocao) {
		repositorio.save(promocao);
	}

}
