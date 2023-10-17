package com.personal.gallery.kafkainternals.core.producer;

import com.personal.gallery.kafkainternals.avro.pojo.MatchStats;
import com.personal.gallery.kafkainternals.core.abstraction.ProducerLayer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Component
public class KafkaProducer implements ProducerLayer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC_1 = "simple-world-cup-events";
    private static final String TOPIC_2 = "world-cup-events";

    //template without serialization
    private final KafkaTemplate<String, String> simpleProducerKafkaTemplate;
    //template with serialization
    private final KafkaTemplate<String, MatchStats> producerKafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> simpleProducerKafkaTemplate, KafkaTemplate<String, MatchStats> producerKafkaTemplate) {
        this.simpleProducerKafkaTemplate = simpleProducerKafkaTemplate;
        this.producerKafkaTemplate = producerKafkaTemplate;
    }

    @Override
    public void publishMessageToKafkaWithSyncAck(Object matchStats) {

        try {
            Message<String> message = MessageBuilder.withPayload(matchStats.toString()).setHeader(KafkaHeaders.TOPIC,TOPIC_1).setHeader(KafkaHeaders.KEY,"wc_match_12").build();
            CompletableFuture<SendResult<String, String>> future =  simpleProducerKafkaTemplate.send(message);
            RecordMetadata recordMetadata = future.get().getRecordMetadata();
            logger.info("Message written to partition {} with offset {}",
                    recordMetadata.partition(),
                    recordMetadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("unable to send message= {}", matchStats, e);
            Thread.currentThread().interrupt();
        } finally {
            producerKafkaTemplate.flush();

        }
    }
    @Override
    public void publishMessageToKafkaWithAsyncAck(Object matchStats) {

        try {
            CompletableFuture<SendResult<String, String>> future = simpleProducerKafkaTemplate.send(TOPIC_1, "wc_match_12", matchStats.toString());
            future.whenCompleteAsync((result, exception) -> {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                if (exception != null)
                    logger.error("unable to send message= {}", matchStats, exception);
                else
                    logger.info("sent message= {} with offset= {}", result.getProducerRecord(), recordMetadata.offset());
            });
        } finally {
            producerKafkaTemplate.flush();
        }
    }

    @Override
    public  void publishMessageToKafkaWithSyncAckAndSerialize(MatchStats matchStats){
        try {
            CompletableFuture<SendResult<String, MatchStats>> future = producerKafkaTemplate.send(TOPIC_2, "wc_match_12", matchStats);
            future.whenCompleteAsync((result, exception) -> {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                if (exception != null)
                    logger.error("unable to send message= {}", matchStats, exception);
                else
                    logger.info("sent message= {} with offset= {}", result.getProducerRecord(), recordMetadata.offset());
            });
        } finally {
            producerKafkaTemplate.flush();
        }
    }
}
