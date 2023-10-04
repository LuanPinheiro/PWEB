package br.edu.ifba.trabalho.dtos;

import java.util.Calendar;

import br.edu.ifba.trabalho.models.Consulta;

public record ConsultaListar(MedicoListar medico, PacienteListar paciente, Calendar data) {
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
				consulta.getDataHora()
		);
	}
}
