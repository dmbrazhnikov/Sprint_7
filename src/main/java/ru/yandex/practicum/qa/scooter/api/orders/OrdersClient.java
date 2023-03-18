package ru.yandex.practicum.qa.scooter.api.orders;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.yandex.practicum.qa.scooter.api.BaseClient;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;


public class OrdersClient extends BaseClient {

    @Step("Отправить POST-запрос создания заказа")
    public Response sendCreateOrderRequest(ScooterOrder scooterOrder) {
        Response response = null;
        try {
            response = given()
                    .config(config)
                    .contentType(ContentType.JSON)
                    .body(scooterOrder)
                    .post("/orders");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return response;
    }

    @Step("Отправить GET-запрос получения списка заказов")
    public Response sendGetOrdersRequest() {
        Response response = null;
        try {
            response = given()
                    .config(config)
                    .get("/orders");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return response;
    }
}