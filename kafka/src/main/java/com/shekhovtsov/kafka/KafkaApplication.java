package com.shekhovtsov.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

}

//эти конфиги дублируем в каждый микросервис - сделать сервис нотификации
//скачать кафка тулл создавать топики
