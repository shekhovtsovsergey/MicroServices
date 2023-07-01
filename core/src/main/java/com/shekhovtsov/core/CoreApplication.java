package com.shekhovtsov.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}


//+перевести на постгресс
//+все конфиги раскидать по сервисам
//+убрать эврику и конфиги
//+пакеты переназвать вместо example
//+фабрика которой отдавался бы проперти и эта фабрика выдавала веб клиент в любой сервис (спрятать 20 повторений аппконфиг)
//+переименовать в репозитории DAO
//+private BigDecimal totalPrice;
//+округление сделать
//+перейти на spring data JDBC(без спецификации)
//+скачать картинку убрать из кора, фронт должен спросить потом в пикче***********потом****


//оживить JDBC исправить ошибку(org.springframework.data.mapping.MappingException: Couldn't find PersistentEntity for type class java.lang.Long)
//Сделать новый модуль "Бонусы"
    //при покупке начислять бонусы клиенту
    //у бонусов должно быть время жизни
    //по клиенту можно запросить общее колич бонусов
    //по клиенту детализацию бонусов
    //по расписанию сервис должен проверять раз в день все бонусы которые есть просрочились их удалять формат "кроновский"+сделать чтобы запускалось на 1 поде (core растиражирован на разные поды БД)
    //возможность списать бонусы при оформлении заказа
    //свагер описать в бонусах
//контроллер сделать отдельный метод сеарч в который передается JSON с параметрами и ищется нужный продукт по параметрам JSON (POST) findProducts (мин макс цена и все)



//ResourceNotFoundException вынести из GlobalExceptionHandler.java
//контроллер createNewProduct попробовать сложить параметры в JSON
//во всех ответах избавиться от Листов перейти к стринице или к обьекту в контроллере
//private String code;//передать код текстовый ORDER_DRAFT_NOT_FOUND
//в свагере конфигурировать несколько версий
//бонус и сервис нотификации
//перевести спецификацию на spring data JDBC
//кафка подключить
