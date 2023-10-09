package br.edu.ifba.consulta.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifba.consulta.models.Consulta;
import jakarta.validation.constraints.Future;

public record ConsultaListar(
		Long medico,
		Long paciente,
		@Future LocalDate data,
		LocalTime hora,
		boolean desmarcado) {
	public ConsultaListar(Consulta consulta) {
		this(
//				new MedicoListar(
//						consulta.getMedico().getDadosPessoais().getNome(),
//						consulta.getMedico().getDadosPessoais().getEmail(),
//						consulta.getMedico().getCrm(),
//						consulta.getMedico().getEspecialidade()
//				),
//				new PacienteListar(
//						consulta.getPaciente().getDadosPessoais().getNome(),
//						consulta.getPaciente().getDadosPessoais().getEmail(),
//						consulta.getPaciente().getCpf()
//				),
				consulta.getMedico(),
				consulta.getPaciente(),
				consulta.getData(),
				consulta.getHora(),
				consulta.isDesmarcado()
		);
	}
}
