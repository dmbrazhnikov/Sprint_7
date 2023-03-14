package ru.yandex.practicum.qa.scooter.api.orders.stuff;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScooterOrder {

    private String firstName, lastName, address, metroStation, phone, deliveryDate, comment;
    private Integer rentTime;
    private final Set<String> color = new HashSet<>();
    @Setter
    private String createdAt, updatedAt;
    @Setter
    private Integer id, courierId, track, status;
    @Setter
    private Boolean cancelled, finished, inDelivery;

    public ScooterOrder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ScooterOrder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ScooterOrder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ScooterOrder setMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public ScooterOrder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public ScooterOrder setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public ScooterOrder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public ScooterOrder setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
        return this;
    }
}
