package br.edu.ifba.consulta.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("medico-ms")
public interface MedicoClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "medicos/encontrarPorCrm")
	public ResponseEntity<MedicoConsulta> encontrarPorCrm(@RequestBody String crm);
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/encontrarPorEspecialidade/{especialidade}")
	public ResponseEntity<List<MedicoConsulta>> encontrarPorEspecialidade(@PathVariable Especialidade especialidade);
}
