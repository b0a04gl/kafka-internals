package com.personal.gallery.kafkainternals.common;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.KafkaHeaders;

import java.io.IOException;

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
}
