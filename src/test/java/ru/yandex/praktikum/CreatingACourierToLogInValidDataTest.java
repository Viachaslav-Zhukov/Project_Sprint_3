package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@Story("Класс создания курьера и входа в систему с валидными учетными данными")
public class CreatingACourierToLogInValidDataTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = Courier.getRandom();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @Description("С помощью рандомного генератора был создан курьер с валидными данными")
    @DisplayName("Создание курьера с валидными учетными данными")
    public void creatingCourierWithValidData() {
        Courier courier = Courier.getRandom();
        var createResponse = courierClient.create(courier);
        boolean isCreated = createResponse.extract().path("ok");
        int statusCode = createResponse.extract().statusCode();
        courierId = courierClient.login(CourierCredentials.from(courier));

        Assert.assertEquals(SC_CREATED, statusCode);
        Assert.assertTrue(isCreated);
        Assert.assertNotEquals(0, courierId);
    }

    @Test
    @Description("Вход курьера в систему с валидными учетными данными с помощью рандомного генератора")
    @DisplayName("Вход курьера в систему с валидными учетными данными")
    public void LogInWithValidCredentials() {
        var loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = courierClient.login(CourierCredentials.from(courier));

        Assert.assertThat(SC_OK, equalTo(SC_OK));
        Assert.assertNotEquals(0, courierId);
    }
}
