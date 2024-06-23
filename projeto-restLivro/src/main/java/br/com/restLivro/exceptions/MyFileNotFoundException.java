package br.com.restLivro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
  //#upload e dawnload
//essa exeção  e quando retorna um aquirvo que não existe
//exceções personalizado
//"MyFileNotFoundException -- Exceção Meu arquivo não encontrado
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {

	/**
	 * exceções personalisada
	 */
	private static final long serialVersionUID = 1L;

	public MyFileNotFoundException(String ex) {
		super(ex);
	}

	  //sobe a exeção e causa
	public MyFileNotFoundException(String ex, Throwable cause) {
		super(ex, cause);
	}
	
}
