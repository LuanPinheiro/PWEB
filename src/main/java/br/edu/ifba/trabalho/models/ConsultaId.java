package br.edu.ifba.trabalho.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class ConsultaId implements Serializable {
	
	@Column(name = "medico_id")
	@NotNull
	private Long medicoId;
	@Column(name = "paciente_id")
	@NotNull
	private Long pacienteId;
	
	public ConsultaId() {
		
	}
	
	public ConsultaId(Long medicoId, Long pacienteId) {
		this.medicoId = medicoId;
		this.pacienteId = pacienteId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(medicoId, pacienteId);
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
		return Objects.equals(medicoId, other.medicoId) && Objects.equals(pacienteId, other.pacienteId);
	}
}
