package br.com.restLivro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//exceções personalizado
//"ResourceNotFoundException -- ExcecaoDeRecursoNaoEncontrado
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExcecaoDeRecursoNaoEncontrado extends RuntimeException {

	/**
	 * exceções personalisada
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoDeRecursoNaoEncontrado(String ex) {
		super(ex);
	}
  
	
}
