package br.com.email.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.email.dtos.EmailDto;
import br.com.email.service.EmailService;

@Component
public class ConsultaListener {
	
	@Autowired
	EmailService emailService;

    @RabbitListener(queues = "email_enviar")
    public void enviarEmail(@Payload EmailDto dto) {
    	emailService.sendEmail(dto);
    }
}
