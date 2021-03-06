package learn.microservices.multiplicator.configuration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures RabbitMQ via AMQP abstraction.
 */
@Configuration
public class AmqpConfiguration {

    // Configure exchange.
    @Bean
    public TopicExchange challengesTopicExchange(@Value("${amqp.exchange.attempts}") final String exchangeName) {
        return ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    // Replace default Java serializer by the JSON serializer.
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}