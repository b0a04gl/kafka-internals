package com.personal.gallery.kafkainternals.core.abstraction;

import com.personal.gallery.kafkainternals.avro.pojo.MatchStats;

public interface ProducerLayer {
    void publishMessageToKafkaWithSyncAck(Object matchStats);

    void publishMessageToKafkaWithAsyncAck(Object matchStats);

    void publishMessageToKafkaWithSyncAckAndSerialize(MatchStats matchStats);

}
