package br.edu.ifba.trabalho.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifba.trabalho.models.Motivo;

public record ConsultaCancelar(Long idMedico, Long idPaciente, LocalDate data, LocalTime hora, Motivo motivo) {
}
