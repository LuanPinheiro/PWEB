package br.edu.ifba.trabalho.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity(name = "consultas")
public class Consulta {
	
	@EmbeddedId
	private ConsultaId ids;
	
	@ManyToOne
	@MapsId("medicoId")
	@JoinColumn(nullable = false)
	private Medico medico;
	
	@ManyToOne
	@MapsId("pacienteId")
	@JoinColumn(nullable = false)
	private Paciente paciente;
	
	public Consulta() {
	}
	
	public Consulta(Medico medico, Paciente paciente, LocalDate data, LocalTime hora) {
		this.ids = new ConsultaId(medico.getId(), paciente.getId(), data, hora);
		this.medico = medico;
		this.paciente = paciente;
	}

	public ConsultaId getIds() {
		return ids;
	}

	public void setIds(ConsultaId id) {
		this.ids = id;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalDate getData() {
		return this.ids.getData();
	}
	
	public LocalTime getHora() {
		return this.ids.getHora();
	}
}
