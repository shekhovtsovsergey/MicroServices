package com.example.core.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class MessageSerializer implements Serializer<Message> {

    @Override
    public byte[] serialize(String topic, Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            // Handle serialization exception
            return null;
        }
    }
}
