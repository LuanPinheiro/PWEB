package br.edu.ifba.consulta.exceptions;

@SuppressWarnings("serial")
public class ConsultaNotFoundException extends RuntimeException {
	public ConsultaNotFoundException(String message) {
		super(message);
	}
}
