package br.edu.ifba.medico.exceptions;

@SuppressWarnings("serial")
public class RegistroNotFoundException extends Exception {
	String table_name;

	public RegistroNotFoundException(String table_name) {
		super(table_name + " não encontrado");
		this.table_name = table_name;
	}
}
