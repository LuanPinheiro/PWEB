package br.edu.ifba.trabalho.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifba.trabalho.models.Motivo;
import jakarta.validation.constraints.NotNull;

public record ConsultaCancelar(
		@NotNull(message = "É necessário indicar um médico") Long idMedico,
		@NotNull(message = "É necessário indicar um paciente") Long idPaciente,
		@NotNull(message = "É necessário indicar uma data") LocalDate data,
		@NotNull(message = "É necessário indicar uma hora") LocalTime hora,
		@NotNull(message = "É necessário indicar um motivo para cancelamento") Motivo motivo) {
}