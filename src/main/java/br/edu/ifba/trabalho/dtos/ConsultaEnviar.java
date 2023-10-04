package br.edu.ifba.trabalho.dtos;

import java.util.Calendar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaEnviar(
		@NotNull(message = "É necessário indicar um médico") Long idMedico,
		@NotNull(message = "É necessário indicar um paciente") Long idPaciente,
		@NotNull(message = "É necessário indicar uma data") Calendar data) {
}
