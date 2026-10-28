package com.casanovajava.notificacao.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice: "vigia" todos os controllers. Quando um deles lança uma exceção,
// o Spring procura aqui um método que saiba transformá-la em resposta HTTP.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata o EmailException: devolve 500 com uma mensagem curta, no lugar do stack trace.
    // Só a mensagem vai para quem chamou a API; a causa detalhada fica no log do servidor,
    // para não expor dados internos.
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<String> handleEmail(EmailException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
