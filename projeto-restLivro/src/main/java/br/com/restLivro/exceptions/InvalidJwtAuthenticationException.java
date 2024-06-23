package br.com.restLivro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

//essa classe para tratar os erros de autenticação

// InvalidJwtAuthenticationException  --  Exceção de autenticação Jwt inválida
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException {


    //construtor
    public InvalidJwtAuthenticationException(String ex) {
        super(ex);
    }
}
