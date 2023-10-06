package br.edu.ifba.medico.exceptions;

@SuppressWarnings("serial")
public class ConsultaNotFoundException extends Exception {
	public ConsultaNotFoundException(String message) {
		super(message);
	}
}
