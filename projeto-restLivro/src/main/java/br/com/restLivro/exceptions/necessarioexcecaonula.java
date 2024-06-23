package br.com.restLivro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//RequiredObjectIsNullException -- necessarioexcecaonula
//Objeto obrigatório é exceção nula
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class necessarioexcecaonula extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public necessarioexcecaonula() {
		super("Não é permitido persistir um objeto nulo!");
	}	
	
	public necessarioexcecaonula(String ex) {
		super(ex);
	}

}
