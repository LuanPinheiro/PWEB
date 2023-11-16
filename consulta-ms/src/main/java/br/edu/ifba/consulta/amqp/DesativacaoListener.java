package br.edu.ifba.consulta.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.edu.ifba.consulta.services.ConsultaService;

@Component
public class DesativacaoListener {

	@Autowired
	private ConsultaService consultaService;
	
	@RabbitListener(queues = "desativacao_registro")
	public void cancelarRegistro(@Payload DesativacaoDTO desativacao) {
		consultaService.cancelarRegistro(desativacao);
    }
}
