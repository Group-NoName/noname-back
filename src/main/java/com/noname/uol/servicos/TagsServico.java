package com.noname.uol.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.Tags;
import com.noname.uol.repositorios.TagsRepositorio;
import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@Service
public class TagsServico {

	@Autowired
	private TagsRepositorio repositorio;
	
	public List<Tags> findAll(){
		return repositorio.findAll();
	}
	
	public Tags findById(String id) {
		Optional<Tags> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Tag não encontrado"));
		}
	
	public Tags insert(Tags obj) {
		return repositorio.save(obj);
	}
	public Tags update(Tags obj) {
		Tags newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	
	private void updateData(Tags newObj, Tags obj) {
		newObj.setNome(obj.getNome());
		newObj.setProdutos(obj.getProdutos());
	}
	
	public void delete(String id) {
		findById(id);
		repositorio.deleteById(id);
	}
	
	public String tagToString(List<Tags> tags) {
		
		List<String> StringTags = new ArrayList<String>();
		for(Tags tag : tags) {
			StringTags.add(" " + tag);
		}
		
		return String.join(" ", StringTags);
	}

}
