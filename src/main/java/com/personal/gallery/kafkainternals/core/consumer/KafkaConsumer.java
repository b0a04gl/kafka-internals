package com.personal.gallery.kafkainternals.core.consumer;

import com.personal.gallery.kafkainternals.avro.pojo.MatchStats;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @KafkaListener(
            topics = "world-cup-events",
            containerFactory = "concurrentKafkaListenerContainerFactory",
            groupId = "simple-consumer-local"
    )
    public void listen(List<MatchStats> message, Acknowledgment ack) {
        logger.info("consumed message : {} ",message);
        ack.acknowledge();
    }
}
