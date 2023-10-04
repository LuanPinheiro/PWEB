package br.edu.ifba.trabalho.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class ConsultaId implements Serializable {
	
	@Column(name = "medico_id", nullable = false)
	private Long medicoId;
	
	@Column(name = "paciente_id", nullable = false)
	private Long pacienteId;
	
	@Column(name = "data_hora", nullable = false)
	private Calendar dataHora;
	
	public ConsultaId() {
		
	}
	
	public ConsultaId(Long medicoId, Long pacienteId, Calendar dataHora) {
		this.medicoId = medicoId;
		this.pacienteId = pacienteId;
		this.dataHora = dataHora;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataHora, medicoId, pacienteId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultaId other = (ConsultaId) obj;
		return Objects.equals(dataHora, other.dataHora) && Objects.equals(medicoId, other.medicoId)
				&& Objects.equals(pacienteId, other.pacienteId);
	}

	public Calendar getDataHora() {
		return this.dataHora;
	}
}
