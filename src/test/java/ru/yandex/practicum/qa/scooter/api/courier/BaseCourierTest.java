package ru.yandex.practicum.qa.scooter.api.courier;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.qa.scooter.api.courier.stuff.Courier;
import ru.yandex.practicum.qa.scooter.api.courier.stuff.CourierClient;


public class BaseCourierTest {

    Courier courier = null;
    CourierClient courierClient;

    @BeforeEach
    void setUp() {
        courier = new Courier();
        courierClient = new CourierClient();
    }

    @AfterEach
    void cleanup() {
        String id;
        if (courier.getId() == null) {
            Response response = courierClient.sendPostToSignIn(courier);
            id = response.body().jsonPath().getString("id");
        } else
            id = courier.getId();
        if (id != null) {
            System.out.println("Courier with id = " + id + " successfully deleted");
            courierClient.sendDelete(id);
            courier = null;
        } else
            System.out.println("Courier was not created so no need to cleanup");
    }
}
