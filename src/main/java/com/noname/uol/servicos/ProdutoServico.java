package com.noname.uol.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.dto.produtoDTO;
import com.noname.uol.entidades.Produtos;
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
		newObj.setDescricao(obj.getDescricao());
		newObj.setImages(obj.getImages());
		newObj.setPreco(obj.getPreco());
	}
	
	public Produtos fromDTO(produtoDTO objDto) {
		return new Produtos(objDto.getId(), objDto.getNome(),  objDto.getDescricao(), objDto.getImages(), objDto.getTags(), objDto.getPreco());
	}
}
