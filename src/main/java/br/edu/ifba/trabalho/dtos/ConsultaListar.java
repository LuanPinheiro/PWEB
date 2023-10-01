package br.edu.ifba.trabalho.dtos;

import java.util.Calendar;

import br.edu.ifba.trabalho.models.Consulta;

public record ConsultaListar(MedicoListar medico, PacienteListar paciente, Calendar data) {
	public ConsultaListar(Consulta consulta) {
		this(
				new MedicoListar(
						consulta.getMedico().getNome(),
						consulta.getMedico().getEmail(),
						consulta.getMedico().getCrm(),
						consulta.getMedico().getEspecialidade()
				),
				new PacienteListar(
						consulta.getPaciente().getNome(),
						consulta.getPaciente().getEmail(),
						consulta.getPaciente().getCpf()
				),
				consulta.getDataHora()
		);
	}
}
