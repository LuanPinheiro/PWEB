package br.edu.ifba.trabalho.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record ConsultaEnviar(
		@NotNull(message = "É necessário indicar um médico") Long idMedico,
		@NotNull(message = "É necessário indicar um paciente") Long idPaciente,
		@FutureOrPresent(message = "Data mínima para consulta inválida") @NotNull(message = "É necessário indicar uma data") LocalDate data,
		@NotNull(message = "É necessário indicar um horário") LocalTime hora) {
}
