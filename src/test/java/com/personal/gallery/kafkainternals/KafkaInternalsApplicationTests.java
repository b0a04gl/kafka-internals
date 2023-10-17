package com.personal.gallery.kafkainternals;

import com.personal.gallery.kafkainternals.common.KafkaSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KafkaInternalsApplicationTests {

	@Autowired
	private KafkaSetup kafkaSetup;

	@Test
	void contextLoads() throws IOException {
		assertNotNull(kafkaSetup.simpleKafkaProps());
		assertNotNull(kafkaSetup.kafkaPropsWithSerialisation());

		assertNotNull(kafkaSetup.consumerFactory(kafkaSetup.simpleKafkaProps()));
		assertNotNull(kafkaSetup.producerFactoryString(kafkaSetup.simpleKafkaProps()));

		assertNotNull(kafkaSetup.consumerFactory(kafkaSetup.kafkaPropsWithSerialisation()));
		assertNotNull(kafkaSetup.producerFactoryString(kafkaSetup.kafkaPropsWithSerialisation()));
	}

}
