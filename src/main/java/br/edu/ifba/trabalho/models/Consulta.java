package br.edu.ifba.trabalho.models;

import java.util.Calendar;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
	
	public Consulta(Medico medico, Paciente paciente, Calendar dataHora) {
		this.ids = new ConsultaId(medico.getId(), paciente.getId(), dataHora);
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

	public Calendar getDataHora() {
		return this.ids.getDataHora();
	}
}
