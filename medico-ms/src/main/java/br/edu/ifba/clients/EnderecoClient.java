package br.edu.ifba.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("email-ms")
public interface EnderecoClient {
@RequestMapping(method = RequestMethod.POST, value = 
"/email/send")
public ResponseEntity<EmailDto> sendEmail(@RequestBody EmailDto 
dto);
}

