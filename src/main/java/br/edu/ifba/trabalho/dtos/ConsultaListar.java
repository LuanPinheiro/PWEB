package br.edu.ifba.trabalho.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifba.trabalho.models.Consulta;
import jakarta.validation.constraints.Future;

public record ConsultaListar(
		MedicoListar medico,
		PacienteListar paciente,
		@Future LocalDate data,
		LocalTime hora) {
	public ConsultaListar(Consulta consulta) {
		this(
				new MedicoListar(
						consulta.getMedico().getDadosPessoais().getNome(),
						consulta.getMedico().getDadosPessoais().getEmail(),
						consulta.getMedico().getCrm(),
						consulta.getMedico().getEspecialidade()
				),
				new PacienteListar(
						consulta.getPaciente().getDadosPessoais().getNome(),
						consulta.getPaciente().getDadosPessoais().getEmail(),
						consulta.getPaciente().getCpf()
				),
				consulta.getData(),
				consulta.getHora()
		);
	}
}
