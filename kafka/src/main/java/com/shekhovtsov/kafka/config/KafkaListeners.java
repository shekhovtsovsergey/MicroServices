package com.shekhovtsov.kafka.config;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {


    @KafkaListener(
            topics = "test",
            groupId = "test"
    )
    void listner (String data){
        System.out.println("listner received:" + data);
    }


}
