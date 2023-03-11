package ru.yandex.practicum.qa.scooter.api.courier;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Создание курьера")
public class CourierCreateTest extends BaseCourierTest {

    @Test
    @Order(1)
    @DisplayName("Курьера можно создать")
    @Description("Создание курьера запросом с корректными данными")
    void shouldCreate() {
        courier = new Courier();
        Response response = sendPostToCreateCourier(courier);
        response.then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @Order(2)
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Если попытаться создать пользователя с логином, который уже есть, возвращается ошибка")
    void shouldRejectDuplication() {
        // Arrange
        courier = new Courier();
        courier.setPassword("0394u8iojgf");
        courier.setFirstName("Travis");
        sendPostToCreateCourier(courier);
        // Act & assert
        Response response = sendPostToCreateCourier(courier);
        response.then()
                .statusCode(409)
                .and()
                .assertThat().body("message", containsString("Этот логин уже используется"));
    }

    @Test
    @Order(3)
    @DisplayName("Для создания курьера нужно передать все обязательные поля")
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    void shouldRejectIncompleteCreateJson() {
        // Arrange
        Courier incompleteCourier = new Courier("slowpoke_rodriguez", null, "Slowpoke", null);
        // Act & assert
        Response response = sendPostToCreateCourier(incompleteCourier);
        response.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", containsString("Недостаточно данных для создания учетной записи"));
    }
}