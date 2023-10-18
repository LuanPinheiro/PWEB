package br.com.email.amqp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Configuration
@Component
@EnableAutoConfiguration
public class RabbitConfig {
	
	@Bean
	public Queue queue() {
		return new Queue("email.enviar", false);
	}
}
