package br.edu.ifba.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifba.consulta.models.Paciente;

@FeignClient("paciente-ms")
public interface PacienteClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/pacientes/encontrarPorId/{id}")
	public ResponseEntity<Paciente> encontrarPorId(@PathVariable Long id);
}