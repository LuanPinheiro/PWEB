package br.edu.ifba.consulta.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity(name = "consultas")
public class Consulta{
	
	@EmbeddedId
	private ConsultaId ids;
	
	private boolean desmarcado;
	
	@Enumerated(EnumType.STRING)
	private Motivo motivo;
	
	public Consulta() {
	}
	
	public Consulta(String medico, String paciente, LocalDate data, LocalTime hora) {
		this.ids = new ConsultaId(medico, paciente, data, hora);
		this.desmarcado = false;
		this.motivo = null;
	}

	public ConsultaId getIds() {
		return ids;
	}

	public void setIds(ConsultaId id) {
		this.ids = id;
	}

	public String getMedico() {
		return this.ids.getMedicoId();
	}

	public void setMedico(String medico) {
		this.ids.setMedicoId(medico);
	}

	public String getPaciente() {
		return this.ids.getPacienteId();
	}

	public void setPaciente(String paciente) {
		this.ids.setPacienteId(paciente);
	}

	public LocalDate getData() {
		return this.ids.getData();
	}
	
	public LocalTime getHora() {
		return this.ids.getHora();
	}

	public boolean isDesmarcado() {
		return desmarcado;
	}

	public void setDesmarcado(boolean desmarcado) {
		this.desmarcado = desmarcado;
	}
	public Motivo getMotivo() {
		return motivo;
	}
	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}
}
