package br.edu.ifba.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("paciente-ms")
public interface PacienteClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/pacientes/encontrarPorCpf")
	public ResponseEntity<PacienteConsulta> encontrarPorCpf(@RequestParam("cpf") String cpf);
}