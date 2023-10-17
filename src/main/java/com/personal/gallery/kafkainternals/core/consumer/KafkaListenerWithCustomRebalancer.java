package com.personal.gallery.kafkainternals.core.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaListenerWithCustomRebalancer implements ConsumerSeekAware {
    private final Map<TopicPartition, OffsetAndMetadata> savedOffsets = new HashMap<>();

    private ConsumerSeekCallback consumerSeekCallback;

    @KafkaListener(
            topics = "world-cup-events",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, String> consumerRecord) {

        OffsetAndMetadata metadata = new OffsetAndMetadata(consumerRecord.offset() + 1, "no metadata");
        savedOffsets.put(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()), metadata);
    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
        this.consumerSeekCallback = callback;
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        for (TopicPartition partition : assignments.keySet()) {
            OffsetAndMetadata offset = savedOffsets.get(partition);
            if (offset != null) {
                callback.seek("world-cup-events", partition.partition(), offset.offset());
            }
        }
    }
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        for (TopicPartition partition : partitions) {
            OffsetAndMetadata offset = savedOffsets.get(partition);
            if (offset != null) {
                consumerSeekCallback.seek("world-cup-events", partition.partition(), offset.offset());
                savedOffsets.remove(partition);
            }
        }
    }

}