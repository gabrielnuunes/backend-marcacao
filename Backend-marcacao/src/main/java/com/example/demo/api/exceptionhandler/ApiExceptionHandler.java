package com.example.demo.api.exceptionhandler;

import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(EntidadeNaoEncontradaException.class)
//    public ResponseEntity<?> tratarEntidadeNaoEncontradaExcetion(EntidadeNaoEncontradaException e) {
//
//        Problem problema = Problem.builder()
//                .dataHora(LocalDateTime.now())
//                .mensagem(e.getMessage()).build();
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
//    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaExcetion(
            EntidadeNaoEncontradaException exception, WebRequest request) {

        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(NegocioException e) {

        Problem problema = Problem.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .dataHora(LocalDateTime.now())
                    .mensagem(status.getReasonPhrase()).build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .dataHora(LocalDateTime.now())
                    .mensagem((String) body).build();

        }

        return super.handleExceptionInternal(exception, body, headers, status, request);
    }
}
