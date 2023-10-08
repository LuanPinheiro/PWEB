package br.edu.ifba.consulta.exceptions;

@SuppressWarnings("serial")
public class ConsultaNotFoundException extends Exception {
	public ConsultaNotFoundException(String message) {
		super(message);
	}
}
