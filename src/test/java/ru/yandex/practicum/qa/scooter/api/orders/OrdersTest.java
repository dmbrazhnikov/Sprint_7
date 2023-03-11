package ru.yandex.practicum.qa.scooter.api.orders;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import ru.yandex.practicum.qa.scooter.api.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Тесты эндпойнта /api/v1/orders")
public class OrdersTest extends BaseTest {

    @ParameterizedTest
    @DisplayName("Создание заказа")
    @CsvSource({"BLACK,", "GREY,", "BLACK,GREY", ","})
    void shouldCreateOrder(ArgumentsAccessor argumentsAccessor) {
        Order order = new Order();
        order.setFirstName("Daniel").setLastName("Kahneman").setAddress("Princeton, New Jersey").setMetroStation("4")
                .setPhone("+7 800 355 35 35").setDeliveryDate("2023-07-08").setComment("Thinking, Fast and Slow").setRentTime(5);
        for (int i = 0; i < argumentsAccessor.size(); i++) {
            String color = argumentsAccessor.get(i, String.class);
            if (color != null) order.getColor().add(color);
        }
        Response response = sendCreateOrderRequest(order);
        response.then()
                .statusCode(201)
                .and()
                .assertThat().body("track", notNullValue(Integer.class));
    }

    @Test
    @DisplayName("Получение списка заказов")
    void shouldReturnOrders() {
        Response response = sendGetOrdersRequest();
        response.then().statusCode(200);
        List<Order> orders = response.jsonPath().getList("orders", Order.class);
        assertFalse(orders.isEmpty());
    }

    @Step("Отправить POST-запрос создания заказа")
    Response sendCreateOrderRequest(Order order) {
        Response response = null;
        try {
            response = given()
                    .config(config)
                    .contentType(ContentType.JSON)
                    .body(order)
                    .post("/orders");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return response;
    }

    @Step("Отправить GET-запрос получения списка заказов")
    Response sendGetOrdersRequest() {
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
