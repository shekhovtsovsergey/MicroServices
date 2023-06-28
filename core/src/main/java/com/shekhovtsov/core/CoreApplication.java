package com.shekhovtsov.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}


//+перевести на постгресс
//+все конфиги раскидать по сервисам
//+убрать эврику и конфиги
//+пакеты переназвать вместо example
//+фабрика которой отдавался бы проперти и эта фабрика выдавала веб клиент в любой сервис (спрятать 20 повторений ааппконфиг)
//переименовать в репозитории DAO
//private BigDecimal totalPrice;
//округление сделать


//перейти на spring data JDBC
//в свагере конфигурировать несколько версий
//контроллер сделать отдельный метод сеарч в который передается JSON с параметрами и ищется нужный продукт по параметрам JSON (POST) findProducts
//ResourceNotFoundException вынести из GlobalExceptionHandler.java
//контроллер createNewProduct попробовать сложить параметры в JSON
//сделать черновики заказа
//во всех ответах избавиться от Листов перейти к стринице или к обьекту в контроллере
//скачать картинку убрать из кора, фронт должен спросить потом в пикче***********потом****
//private String code;//передать код текстовый ORDER_DRAFT_NOT_FOUND
//бонус и сервис нотификации
// @GetMapping("/api/v1/products") перенести корень - низкий приоритет можно не переделывать