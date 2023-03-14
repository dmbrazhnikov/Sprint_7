package ru.yandex.practicum.qa.scooter.api.courier.stuff;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    private String login = "speedy_gonzales", password = "1234qwerty", firstName = "Speedy", id = null;
}
