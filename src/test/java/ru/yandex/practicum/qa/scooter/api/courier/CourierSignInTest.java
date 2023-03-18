package ru.yandex.practicum.qa.scooter.api.courier;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Авторизация курьера")
public class CourierSignInTest extends BaseCourierTest {

    @Test
    @Order(1)
    @DisplayName("Корректный запрос")
    @Description("Авторизация успешно созданного курьера корректным запросом.")
    void shouldLogIn() {
        courierClient.sendPostToCreate(courier);
        Response response = courierClient.sendPostToSignIn(courier);
        response.then()
                .statusCode(200)
                .and()
                .body("id", notNullValue(String.class));
        courier.setId(response.body().jsonPath().getString("id"));
    }

    @Test
    @Order(2)
    @DisplayName("С пустым паролем")
    void shouldRejectSignInWithEmptyPassword() {
        // Arrange
        courierClient.sendPostToCreate(courier);
        // Act & assert
        courierClient.sendPostToSignIn(new Courier(courier.getLogin(), "", courier.getFirstName(), null))
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    @Order(3)
    @DisplayName("С пустым логином")
    void shouldRejectSignInWithEmptyLogin() {
        // Arrange
        courierClient.sendPostToCreate(courier);
        // Act & assert
        courierClient.sendPostToSignIn(new Courier("", courier.getPassword(), courier.getFirstName(), null))
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    @Order(4)
    @DisplayName("С отсутствующим паролем")
    void shouldRejectWrongPassword() {
        // Arrange
        courierClient.sendPostToCreate(courier);
        // Act & assert
        courierClient.sendPostToSignIn(new Courier(courier.getLogin(), null, courier.getFirstName(), null))
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    @Order(5)
    @DisplayName("Несуществующая учётная запись")
    void shouldRejectNonExisting() {
        courierClient.sendPostToSignIn(new Courier("iaoebgrvndv", "aeioufndviaj", "eaurgBUKZSDHFN", null))
                .then()
                .statusCode(404)
                .and()
                .assertThat().body("message", containsString("Учетная запись не найдена"));
    }
}
