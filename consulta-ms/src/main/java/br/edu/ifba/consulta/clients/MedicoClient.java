package br.edu.ifba.consulta.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("medico-ms")
public interface MedicoClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/encontrarPorId/{id}")
	public ResponseEntity<Long> encontrarPorId(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/encontrarPorEspecialidade/{especialidade}")
	public ResponseEntity<List<Long>> encontrarPorEspecialidade(@PathVariable Especialidade especialidade);
}
