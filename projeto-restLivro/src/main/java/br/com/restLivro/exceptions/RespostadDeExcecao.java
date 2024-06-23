package br.com.restLivro.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

//ExceptionResponse -- RespostadDeExcecao
public class RespostadDeExcecao {
  
	//ExceptionResponse
	//exceções personalizada
	
	private Date timestamp;
	private String messege;
	private String details;
	
	//metodo construtor
	public RespostadDeExcecao(Date timestamp, String messege, String details) {
		super();
		this.timestamp = timestamp;
		this.messege = messege;
		this.details = details;
	}
	

	public RespostadDeExcecao(RespostadDeExcecao exceptionResponse, HttpStatus internalServerError) {
		// TODO Auto-generated constructor stub
	}


	public Date getTimestamp() {
		return timestamp;
	}	

	public String getMessege() {
		return messege;
	}	

	public String getDetails() {
		return details;
	}

	
	
	
	
	
}
