package ru.yandex.practicum.qa.scooter.api.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import ru.yandex.practicum.qa.scooter.api.BaseTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;


public class BaseCourierTest extends BaseTest {

    Courier courier = null;

    @AfterEach
    void cleanup() {
        String id;
        if (courier != null) {
            if (courier.getId() == null) {
                Response response = sendPostToSignIn(courier);
                id = response.body().jsonPath().getString("id");
            } else
                id = courier.getId();
            System.out.println("Courier with id = " + id + " successfully deleted");
            sendDelete(id);
            courier = null;
        }
    }

    @Step("Send POST to /api/v1/courier")
    Response sendPostToCreateCourier(Courier courier) {
        Response rs = null;
        try {
            rs = given()
                    .config(config)
                    .contentType(ContentType.JSON)
                    .body(courier)
                    .post("/courier");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return rs;
    }

    @Step("Send DELETE to /api/v1/courier/{id}")
    Response sendDelete(String courierId) {
        Response rs = null;
        try {
            rs = given()
                    .config(config)
                    .contentType(ContentType.JSON)
                    .body("{\"id\":\"" + courierId + "\"}")
                    .delete("/courier/" + courierId);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return rs;
    }

    @Step("Send POST to /api/v1/courier/login")
    Response sendPostToSignIn(Courier courier) {
        Response rs = null;
        Courier courierAuth = new Courier();
        courierAuth.setLogin(courier.getLogin());
        courierAuth.setPassword(courier.getPassword());
        try {
            rs = given()
                    .config(config)
                    .contentType(ContentType.JSON)
                    .body(courierAuth)
                    .post("/courier/login");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return rs;
    }
}
