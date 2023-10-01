package br.edu.ifba.trabalho.dtos;

import java.util.Calendar;

public record ConsultaEnviar(Long idMedico, Long idPaciente, Calendar data) {
}
