package br.edu.ifba.consulta.exceptions;

@SuppressWarnings("serial")
public class DataInvalidaException extends RuntimeException {

	public DataInvalidaException(String message) {
		super(message);
	}
}
