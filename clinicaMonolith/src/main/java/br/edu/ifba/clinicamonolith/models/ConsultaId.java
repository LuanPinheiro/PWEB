package br.edu.ifba.clinicamonolith.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ConsultaId implements Serializable {
	
	@Column(name = "medico_id", nullable = false)
	private Long medicoId;
	
	@Column(name = "paciente_id", nullable = false)
	private Long pacienteId;
	
	@Column(name = "data", nullable = false)
	private LocalDate data;
	
	@Column(name = "hora", nullable = false)
	private LocalTime hora;
	
	public ConsultaId() {
		
	}
	
	public ConsultaId(Long medicoId, Long pacienteId, LocalDate data, LocalTime hora) {
		this.medicoId = medicoId;
		this.pacienteId = pacienteId;
		this.data = data;
		this.hora = hora;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(data, hora, medicoId, pacienteId);
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
		return Objects.equals(data, other.data) && Objects.equals(hora, other.hora)
				&& Objects.equals(medicoId, other.medicoId) && Objects.equals(pacienteId, other.pacienteId);
	}

	public LocalDate getData() {
		return this.data;
	}
	
	public LocalTime getHora() {
		return this.hora;
	}
}
