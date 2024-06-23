package br.com.restLivro.exceptions.treinador;

import java.util.Date;

import br.com.restLivro.exceptions.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.restLivro.exceptions.ExcecaoDeRecursoNaoEncontrado;
import br.com.restLivro.exceptions.RespostadDeExcecao;

//handler
//class CustomizedResponseEntityExceptionHandle

/**
 * classe que manipular as exceções personalizada
 */
//CustomizedResponseEntityExceptionHandler -- ManipuladorDeExceçãoDeEntidadeDeRespostaPersonalizado
@ControllerAdvice
@RestController
public class ManipuladorDeExceçãoDeEntidadeDeRespostaPersonalizado extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<RespostadDeExcecao> handleAllExceptions(
			     Exception ex, WebRequest request){
	
		RespostadDeExcecao exceptionResponse = new RespostadDeExcecao(
				          new Date(),
				           ex.getMessage(),
				           request.getDescription(false));
	 
		return new ResponseEntity<>(exceptionResponse,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ExcecaoDeRecursoNaoEncontrado.class)
	public final ResponseEntity<RespostadDeExcecao> handlerNotFoundExceptions(
			    Exception ex, WebRequest request){
		
		RespostadDeExcecao exceptionResponse = new RespostadDeExcecao(
			     new Date(),
			     ex.getMessage(),
			     request.getDescription(false)); 
			
		  return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
		}

		//esse metodo ---lidar com exceções de autenticação Jwt inválidas
	//InvalidJwtAuthenticationException -- lidar com exceções de autenticação Jwt inválidas
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<RespostadDeExcecao> handleInvalidJwtAuthenticationExceptions(
			Exception ex, WebRequest request){

		RespostadDeExcecao exceptionResponse = new RespostadDeExcecao(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}
	}










