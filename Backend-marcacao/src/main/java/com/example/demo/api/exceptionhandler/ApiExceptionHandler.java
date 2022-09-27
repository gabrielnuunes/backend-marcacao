package com.example.demo.api.exceptionhandler;

import com.example.demo.domain.exception.EntidadeEmUsoException;
import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
    	ProblemType prolemType = ProblemType.MENSAGEM_NAO_LEGIVEL;
    	String detail = "O corpo da requisição está inválido. Verifique se há erro de sintaxe.";
    	
    	Problem problem = createProblemBuilder(status, prolemType, detail).build();
    	
    	return handleExceptionInternal(ex, problem,
    			new HttpHeaders(), status, request);
    	
	}
	
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaExcetion(
            EntidadeNaoEncontradaException exception, WebRequest request) {

    	HttpStatus status = HttpStatus.NOT_FOUND;
    	ProblemType prolemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
    	String detail = exception.getMessage();
    	
    	Problem problem = createProblemBuilder(status, prolemType, detail).build();
    	
    	return handleExceptionInternal(exception, problem,
    			new HttpHeaders(), status, request);
    	
    	
//    	Problem problem = Problem.builder()
//    			.status(status.value())
//    			.type("http://pacientes.com.br/entidade-nao-encontrada")
//    			.detail(exception.getMessage())
//    			.build();
    	
    }
    
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarEntidadeEmUsoException(
    		EntidadeEmUsoException ex, WebRequest request ) {
    	
    	HttpStatus status = HttpStatus.CONFLICT;
    	ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
    	String detail = ex.getMessage();
    	
    	Problem problem = createProblemBuilder(status, problemType, detail).build();
    	
    	return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    	
    } 

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(
    		NegocioException e, WebRequest request) {
    	
    	return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    	
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
            		.title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(exception, body, headers, status, request);
    }
    
    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, 
    		ProblemType problemType, String detail) {
    	
    	return Problem.builder()
    			.status(status.value())
    			.type(problemType.getUri())
    			.title(problemType.getTitle())
    			.detail(detail);
    }
    
    
}
