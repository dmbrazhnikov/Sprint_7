package ru.yandex.practicum.qa.scooter.api.orders;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.qa.scooter.api.BaseTest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;

@DisplayName("Получить заказ по его номеру")
public class GetOrderByNumberTest extends BaseTest {

    @Test
    @DisplayName("Корректный запрос")
    void shouldReturnOrderByItsTrack() {
        int track = 954580;
        Response response = given()
                .config(config)
                .queryParam("t", track)
                .get("/orders/track");
        response.then().statusCode(200);
        Order order = response.jsonPath().getObject("order", Order.class);
        assertEquals(track, order.getTrack());
    }

    @Test
    @DisplayName("Без номера")
    void shouldReturnInsufficientDataError() {
        given().config(config)
                .when()
                .get("/orders/track")
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", containsString("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("С несуществующим номером")
    void shouldReturnOrderNotFoundError() {
        given().config(config)
                .queryParam("t", 1111111111)
                .when()
                .get("/orders/track")
                .then()
                .statusCode(404)
                .and()
                .assertThat().body("message", containsString("Заказ не найден"));
    }
}
