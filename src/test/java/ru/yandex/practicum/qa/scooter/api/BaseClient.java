package ru.yandex.practicum.qa.scooter.api;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;


public class BaseClient {

    protected static RestAssuredConfig config;

    static {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru/api/v1";
        config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.socket.timeout", 10000)
                        .setParam("http.connection.timeout", 10000));
    }
}
