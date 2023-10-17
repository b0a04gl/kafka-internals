package com.personal.gallery.kafkainternals.common;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.util.Map;

@Configuration
@Lazy
public class KafkaSetup {
    @Bean
    @Qualifier("simpleKafkaProps")
    public KafkaProperties simpleKafkaProps() throws IOException {
        return FileUtils.getKafkaProperties("classpath:KafkaSimpleConfig.json", KafkaProperties.class);
    }


    @Bean
    public <T> ProducerFactory<String, T> simpleProducerFactory(@Qualifier("simpleKafkaProps") KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    @Qualifier("simpleProducerKafkaTemplate")
    public <T> KafkaTemplate<String, T> simpleProducerKafkaTemplate(@Qualifier("simpleProducerFactory") ProducerFactory<String, T> producerFactory, @Qualifier("kafkaPropsWithSerialisation") KafkaProperties kafkaProperties) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    @Primary
    @Qualifier("kafkaPropsWithSerialisation")
    public KafkaProperties kafkaPropsWithSerialisation() throws IOException {
        return FileUtils.getKafkaProperties("classpath:kafkaConfig.json", KafkaProperties.class);
    }

    @Bean
    public <T> ProducerFactory<String, T> producerFactoryString(@Qualifier("kafkaPropsWithSerialisation") KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    @Qualifier("producerKafkaTemplate")
    public <T> KafkaTemplate<String, T> producerKafkaTemplate(@Qualifier("producerFactoryString") ProducerFactory<String, T> producerFactory, @Qualifier("kafkaPropsWithSerialisation") KafkaProperties kafkaProperties) {
        String topic = kafkaProperties.getProducer().getProperties().get(KafkaHeaders.TOPIC);
        KafkaTemplate<String, T> template = new KafkaTemplate<>(producerFactory);
        if (!StringUtils.isBlank(topic)) template.setDefaultTopic(topic);
        return template;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, Object> consumerFactory(@Qualifier("kafkaPropsWithSerialisation") KafkaProperties kafkaProperties) {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        consumerProperties.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory(@Qualifier("consumerFactory") ConsumerFactory<String, Object> consumerFactory, @Qualifier("kafkaPropsWithSerialisation") KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(kafkaProperties.getListener().getAckMode());
        factory.setConcurrency(kafkaProperties.getListener().getConcurrency());

        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        if (consumerProperties.containsKey("stringToJSON")
                && consumerProperties.get("stringToJSON").equals("true")) {
            BatchMessagingMessageConverter messageConverter =
                    new BatchMessagingMessageConverter(new StringJsonMessageConverter());
            factory.setMessageConverter(messageConverter);
        }
        if (kafkaProperties.getListener().getType().name().equalsIgnoreCase("BATCH")) {
            factory.setBatchListener(true);
        }
        return factory;
    }

}
