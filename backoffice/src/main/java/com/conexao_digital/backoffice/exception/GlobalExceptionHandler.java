package com.conexao_digital.backoffice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsuarioBackofficeException.class)
    public ResponseEntity<String> handleUsuarioBackofficeException(UsuarioBackofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProdutoBackofficeException.class)
    public ResponseEntity<String> handleProdutoBackofficeException(ProdutoBackofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ImagemProdutoBackofficeException.class)
    public ResponseEntity<String> handleImagemProdutoBackofficeException(ImagemProdutoBackofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}