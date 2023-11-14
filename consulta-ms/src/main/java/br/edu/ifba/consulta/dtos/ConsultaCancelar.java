package br.edu.ifba.consulta.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifba.consulta.models.Motivo;
import jakarta.validation.constraints.NotNull;

public record ConsultaCancelar(
		@NotNull(message = "É necessário indicar um médico") String crmMedico,
		@NotNull(message = "É necessário indicar um paciente") String cpfPaciente,
		@NotNull(message = "É necessário indicar uma data") LocalDate data,
		@NotNull(message = "É necessário indicar uma hora") LocalTime hora,
		@NotNull(message = "É necessário indicar um motivo para cancelamento") Motivo motivo) {
}