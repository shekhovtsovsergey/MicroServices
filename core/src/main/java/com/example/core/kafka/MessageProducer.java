package com.example.core.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final String topic;

    @Autowired
    public MessageProducer(KafkaTemplate<String, Message> kafkaTemplate,
                           @Value("${application.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(Message message) {
        kafkaTemplate.send(topic, message);
    }

    public void generateAndSendMessage() {
        Message message = new Message();
        message.setSender("akhj@mail.ru");
        message.setReceiver("89629417575@mail.ru");
        message.setText("Ваш заказ оформлен успешно!");
        sendMessage(message);
        System.out.printf("message send");
    }


}
