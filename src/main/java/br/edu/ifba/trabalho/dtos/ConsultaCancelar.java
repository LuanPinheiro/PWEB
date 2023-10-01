package br.edu.ifba.trabalho.dtos;

import java.util.Calendar;

import br.edu.ifba.trabalho.models.Motivo;

public record ConsultaCancelar(Long idMedico, Long idPaciente, Calendar data, Motivo motivo) {
}
