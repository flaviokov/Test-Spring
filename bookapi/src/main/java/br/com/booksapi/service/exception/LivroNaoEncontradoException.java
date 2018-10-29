package br.com.booksapi.service.exception;

public class LivroNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6766031668242492377L;
	
	public LivroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	
	public LivroNaoEncontradoException(String mensagem, Throwable e) {
		super(mensagem,e);
	}

}
