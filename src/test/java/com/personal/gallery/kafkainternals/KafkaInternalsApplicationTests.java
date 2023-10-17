package com.personal.gallery.kafkainternals;

import com.personal.gallery.kafkainternals.common.KafkaSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
class KafkaInternalsApplicationTests {

	@Autowired
	private KafkaSetup kafkaSetup;

	@Test
	void validateConfigLoading() throws IOException {
		assertNotNull(kafkaSetup.simpleKafkaProps());
		assertNotNull(kafkaSetup.kafkaPropsWithSerialisation());

		assertNotNull(kafkaSetup.consumerFactory(kafkaSetup.simpleKafkaProps()));
		assertNotNull(kafkaSetup.producerFactoryString(kafkaSetup.simpleKafkaProps()));

		assertNotNull(kafkaSetup.consumerFactory(kafkaSetup.kafkaPropsWithSerialisation()));
		assertNotNull(kafkaSetup.producerFactoryString(kafkaSetup.kafkaPropsWithSerialisation()));
	}

}
