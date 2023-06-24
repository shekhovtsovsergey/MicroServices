package com.example.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}


//перевести на постгресс
//все конфиги раскидать по сервисам
//убрать эврику и конфиги
//перейти на spring data JDBC
//пакеты переназвать вместо example
//фабрика которой отдавался бы проперти и эта фабрика выдавала веб клиент в любой сервис (спрятать 20 повторений ааппконфиг)
//в свагере конфигурировать несколько версий
// @GetMapping("/api/v1/products") перенести корень - низкий приоритет можно не переделывать
//контроллер сделать отдельный метод сеарч в который передается JSON с параметрами и ищется нужный продукт по параметрам JSON (POST) findProducts
//ResourceNotFoundException вынести из GlobalExceptionHandler.java
//контроллер createNewProduct попробовать сложить параметры в JSON
//сделать черновики заказа
//во всех ответах избавиться от Листов перейти к стринице или к обьекту в контроллере
//скачать картинку убрать из кора, фронт должен спросить потом в пикче***********потом****
//переименовать в репозитории DAO
//private String code;//передать код текстовый ORDER_DRAFT_NOT_FOUND
//private BigDecimal totalPrice;
//округление сделать
//бонус и сервис нотификации