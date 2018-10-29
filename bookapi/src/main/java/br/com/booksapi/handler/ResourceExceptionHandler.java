package br.com.booksapi.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.booksapi.domain.DetalheLivroError;
import br.com.booksapi.service.exception.LivroNaoEncontradoException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<DetalheLivroError> handlerLivroNaoEncontradoException(LivroNaoEncontradoException e, HttpServletRequest req) {
		
		DetalheLivroError error = new DetalheLivroError();
		error.setStatusCode(404);
		error.setMensagem("Livro não encontrado.");
		error.setTimestamp(System.currentTimeMillis());
		error.setDetalhe("http://erros.socialbook.com/404");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
