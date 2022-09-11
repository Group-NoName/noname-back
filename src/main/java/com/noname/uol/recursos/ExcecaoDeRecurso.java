package com.noname.uol.recursos;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.noname.uol.servicos.excecao.ObjectNotFoundException;

@ControllerAdvice
public class ExcecaoDeRecurso {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<TratamentoDeErro> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
	HttpStatus status = HttpStatus.NOT_FOUND;
	TratamentoDeErro err = new TratamentoDeErro(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
	return ResponseEntity.status(status).body(err);
	}
}
