package ru.yandex.practicum.qa.scooter.api.courier;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import ru.yandex.practicum.qa.scooter.api.BaseClient;


public class CourierClient extends BaseClient {

    private static final String URI_PART = "/courier";

    @Step("Send POST to /api/v1/courier")
    public Response sendPostToCreate(Courier courier) {
        Response rs = null;
        try {
            rs = RestAssured.given()
                    .config(BaseClient.config)
                    .contentType(ContentType.JSON)
                    .body(courier)
                    .post(URI_PART);
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return rs;
    }

    @Step("Send DELETE to /api/v1/courier/{id}")
    public Response sendDelete(String courierId) {
        Response rs = null;
        try {
            rs = RestAssured.given()
                    .config(BaseClient.config)
                    .contentType(ContentType.JSON)
                    .body("{\"id\":\"" + courierId + "\"}")
                    .delete(URI_PART + "/" + courierId);
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return rs;
    }

    @Step("Send POST to /api/v1/courier/login")
    public Response sendPostToSignIn(Courier courier) {
        Response rs = null;
        Courier courierAuth = new Courier();
        courierAuth.setLogin(courier.getLogin());
        courierAuth.setPassword(courier.getPassword());
        try {
            rs = RestAssured.given()
                    .config(BaseClient.config)
                    .contentType(ContentType.JSON)
                    .body(courierAuth)
                    .post(URI_PART + "/login");
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getClass() + " " + e.getMessage());
        }
        return rs;
    }
}
