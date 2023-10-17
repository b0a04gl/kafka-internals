package com.personal.gallery.kafkainternals.application;

import com.personal.gallery.kafkainternals.avro.pojo.MatchStats;
import com.personal.gallery.kafkainternals.core.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class ControlTower {
    private final Logger logger = LoggerFactory.getLogger(ControlTower.class);

    private final KafkaProducer kafkaProducer;

    @Autowired
    public ControlTower(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(path = "/kafka/publish", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> produceMessageToKafka(@RequestBody Object object, @RequestParam("ackMode") String ackMode) throws Exception {
        if(Objects.isNull(object))
            throw new Exception("Some required fields are missing in payload");

        logger.info("API hit for /kafka/publish with ackMode = {}",ackMode);

        if("sync".equals(ackMode))
            kafkaProducer.publishMessageToKafkaWithSyncAck(object);
        else
            kafkaProducer.publishMessageToKafkaWithAsyncAck(object);
        return ResponseEntity.ok("published message to kafka");
    }


    @PostMapping(path = "/kafka/publish/serialize", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> produceMessageToKafkaWithSerialization(@RequestBody MatchStats matchStats) throws Exception {
        if(Objects.isNull(matchStats))
            throw new Exception("Some required fields are missing in payload");

        kafkaProducer.publishMessageToKafkaWithSyncAckAndSerialize(matchStats);

        return ResponseEntity.ok("published serialized message to kafka");
    }
}
