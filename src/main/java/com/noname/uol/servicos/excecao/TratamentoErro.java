package com.noname.uol.servicos.excecao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noname.uol.entidades.InformacaoErro;
import com.noname.uol.entidades.Produtos;
import com.noname.uol.entidades.Tags;
import com.noname.uol.servicos.TagsServico;

import lombok.Data;

@Service
@Data
public class TratamentoErro<T extends InformacaoErro> {

	private Boolean hasError;
	private String errorLog;
	
	public TratamentoErro(){ }
	
	public void verificarCopiaEntreListas(List<T> listaAComparar, List<T> listaBase) {
		setErrorLog(""); 
		setHasError(false);
		for (T objeto : listaBase) 
			
			if(listaAComparar.contains(objeto)) {
				setHasError(true);
				setErrorLog(getErrorLog() + " " + objeto.obterNome()); 
			}
	}
	public void verificarCopiaItemUnico(List<T> listaBase, T objetoAComparar) {
		setErrorLog(""); 
		setHasError(false);
		for (T objeto : listaBase) {

			if(objeto.obterNome().equals(objetoAComparar.obterNome())) {

				setHasError(true);
				setErrorLog(getErrorLog() + " " + objeto.obterNome()); 
			}
		}
	}

}
