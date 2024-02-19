package com.example.core.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MessageDeserializer implements Deserializer<Message> {

    @Override
    public Message deserialize(String topic, byte[] data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(data, Message.class);
        } catch (JsonProcessingException e) {
            // Handle deserialization exception
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
