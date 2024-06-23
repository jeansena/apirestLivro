package br.com.restLivro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
  //#upload e dawnload

//exceções personalizado
//"FileStorageException -- Exceção de armazenamento de arquivos
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException {

	/**
	 * exceções personalisada
	 */
	private static final long serialVersionUID = 1L;

	public FileStorageException(String ex) {
		super(ex);
	}

	//sobe a exeção e causa
	public FileStorageException(String ex, Throwable cause) {
		super(ex, cause);
	}
	
}
