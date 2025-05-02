package com.example.demo.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;

import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {
    private static final String EXCHANGE_NAME = "escola.direct";
    private static final String QUEUE_NAME = "aluno.registro";

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange()).with(QUEUE_NAME);
    }

    @PostConstruct
    private void setup() {
        Queue fila = this.queue();
        DirectExchange exchange = this.directExchange();
        Binding vinculo = this.binding(fila);

        amqpAdmin.declareQueue(fila);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareBinding(vinculo);
    }
}
