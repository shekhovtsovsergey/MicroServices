package com.example.core.kafka;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String sender;
    private String receiver;
    private String text;
}

