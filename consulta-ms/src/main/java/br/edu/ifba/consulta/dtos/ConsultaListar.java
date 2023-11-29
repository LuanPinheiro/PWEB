package br.edu.ifba.consulta.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifba.consulta.models.Consulta;
import jakarta.validation.constraints.Future;

public record ConsultaListar(
		String medico,
		String paciente,
		@Future LocalDate data,
		LocalTime hora,
		boolean desmarcado) {
	
	public ConsultaListar(Consulta consulta) {
		this(
			consulta.getMedico(),
			consulta.getPaciente(),
			consulta.getData(),
			consulta.getHora(),
			consulta.isDesmarcado()
		);
	}
}
