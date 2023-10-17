package com.personal.gallery.kafkainternals.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    public static String getInputAsString(String filePath) {
        File resource = null;
        try {
            resource = ResourceUtils.getFile(filePath);
            return Files.readString(resource.toPath(), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static KafkaProperties getKafkaProperties(String fileName, Class<KafkaProperties> sinkClass) throws JsonProcessingException {

        String inputAsString = getInputAsString(fileName);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE);
        JsonNode jsonNodeTree = new ObjectMapper().readTree(inputAsString);
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        return objectMapper.readValue(jsonAsYaml, sinkClass);
    }
}
