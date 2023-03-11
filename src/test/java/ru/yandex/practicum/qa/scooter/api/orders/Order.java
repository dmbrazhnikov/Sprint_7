package ru.yandex.practicum.qa.scooter.api.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private String firstName, lastName, address, metroStation, phone, deliveryDate, comment;
    private Integer rentTime;
    private final Set<String> color = new HashSet<>();
    @Setter
    private String createdAt, updatedAt;
    @Setter
    private Integer id, courierId, track, status;
    @Setter
    private Boolean cancelled, finished, inDelivery;

    public Order setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Order setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public Order setMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public Order setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Order setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
        return this;
    }
}
