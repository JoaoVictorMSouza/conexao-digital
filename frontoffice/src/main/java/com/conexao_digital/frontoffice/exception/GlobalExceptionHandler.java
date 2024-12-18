package com.conexao_digital.frontoffice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProdutoFrontofficeException.class)
    public ResponseEntity<String> handleProdutoFrontofficeException(ProdutoFrontofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ImagemProdutoFrontofficeException.class)
    public ResponseEntity<String> handleImagemProdutoFrontofficeException(ImagemProdutoFrontofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarrinhoFrontofficeException.class)
    public ResponseEntity<String> handleCarrinhoFrontofficeException(CarrinhoFrontofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioFrontofficeException.class)
    public ResponseEntity<String> handleUsuarioFrontofficeException(UsuarioFrontofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EnderecoFrontofficeException.class)
    public ResponseEntity<String> handleEnderecoFrontofficeException(EnderecoFrontofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PedidoFrontofficeException.class)
    public ResponseEntity<String> handlePedidoFrontofficeException(PedidoFrontofficeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}