package br.edu.ifba.medico.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifba.medico.dtos.EnderecoDTO;
import br.edu.ifba.medico.models.Endereco;

@FeignClient("endereco-ms")
public interface EnderecoClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "/enderecos")
	public ResponseEntity<Endereco> gerarEndereco(@RequestBody EnderecoDTO dto);
}

