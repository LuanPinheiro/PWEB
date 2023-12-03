package br.edu.ifba.pacientes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("endereco-ms")
public interface EnderecoClient {
	@RequestMapping(method = RequestMethod.POST, value = "/enderecos")
	public ResponseEntity<EnderecoDTO> gerarEndereco(@RequestBody EnderecoDTO dto);
}
