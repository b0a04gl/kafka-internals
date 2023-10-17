package com.personal.gallery.kafkainternals.core.abstraction;

import com.personal.gallery.kafkainternals.avro.pojo.MatchStats;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerLayer {
    public void listen(ConsumerRecord<String, MatchStats> record);
}
