package com.personal.gallery.kafkainternals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.personal.gallery"
})
public class KafkaInternalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaInternalsApplication.class, args);
	}

}
