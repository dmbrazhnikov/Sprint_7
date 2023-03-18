package ru.yandex.practicum.qa.scooter.api.orders;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import ru.yandex.practicum.qa.scooter.api.orders.stuff.OrdersClient;
import ru.yandex.practicum.qa.scooter.api.orders.stuff.ScooterOrder;
import java.util.List;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@DisplayName("Тесты эндпойнта /api/v1/orders")
public class OrdersTest {

    private OrdersClient ordersClient;

    @BeforeEach
    void setUp() {
        ordersClient = new OrdersClient();
    }

    @ParameterizedTest
    @DisplayName("Создание заказа")
    @CsvSource({"BLACK,", "GREY,", "BLACK,GREY", ","})
    void shouldCreateOrder(ArgumentsAccessor argumentsAccessor) {
        ScooterOrder scooterOrder = new ScooterOrder();
        scooterOrder.setFirstName("Daniel").setLastName("Kahneman").setAddress("Princeton, New Jersey").setMetroStation("4")
                .setPhone("+7 800 355 35 35").setDeliveryDate("2023-07-08").setComment("Thinking, Fast and Slow").setRentTime(5);
        for (int i = 0; i < argumentsAccessor.size(); i++) {
            String color = argumentsAccessor.get(i, String.class);
            if (color != null) scooterOrder.getColor().add(color);
        }
        ordersClient.sendCreateOrderRequest(scooterOrder)
                .then()
                .statusCode(201)
                .and()
                .assertThat().body("track", notNullValue(Integer.class));
    }

    @Test
    @DisplayName("Получение списка заказов")
    void shouldReturnOrders() {
        Response response = ordersClient.sendGetOrdersRequest();
        response.then().statusCode(200);
        List<ScooterOrder> scooterOrders = response.jsonPath().getList("orders", ScooterOrder.class);
        assertFalse(scooterOrders.isEmpty());
    }
}
