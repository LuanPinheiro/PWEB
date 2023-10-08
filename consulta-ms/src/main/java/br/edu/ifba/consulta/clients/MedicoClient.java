package br.edu.ifba.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifba.consulta.models.Especialidade;
import br.edu.ifba.consulta.models.Medico;

@FeignClient("medico-ms")
public interface MedicoClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/encontrarPorId/{id}")
	public ResponseEntity<Medico> encontrarPorId(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/encontrarPorEspecialidade/{especialidade}")
	public ResponseEntity<Medico> encontrarPorEspecialidade(@PathVariable Especialidade especialidade);
}

