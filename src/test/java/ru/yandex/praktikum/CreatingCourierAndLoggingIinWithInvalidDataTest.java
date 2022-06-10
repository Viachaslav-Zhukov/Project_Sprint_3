package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;

@Story("Создание курьера с не валидными учетными данными")
public class CreatingCourierSecondTest {
    private CourierClient courierClient;
    private Courier courier;
    private int statusCode;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @Test
    @Description("Создание курьера по уже созданным данным, с целью проверки создания курьера с повторяющим логином")
    @DisplayName("Создание курьера с повторяющимся логином")
    public void createCourierWithDuplicateCredentials() {
        Courier courier = new Courier();
        courier.setLogin("vfhujif5");
        courier.setPassword("1234");
        courier.setFirstName("маргоша");
        var createSecondCourier = courierClient.create(courier);
        statusCode = createSecondCourier.extract().statusCode();
        String expectedMessage = "Этот логин уже используется. Попробуйте другой.";
        String actualMessage = createSecondCourier.extract().path("message");

        Assert.assertEquals(expectedMessage, actualMessage);
        Assert.assertEquals(SC_CONFLICT, statusCode);
    }

    @Test
    @DisplayName("Запрос на создание курьера без логина")
    public void CreateCourierWithEmptyLogin() {
        courier.setLogin(null);
        var createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = createResponse.extract().path("message");

        Assert.assertEquals(expectedMessage, actualMessage);
        Assert.assertEquals(SC_BAD_REQUEST, statusCode);

    }

    @Test
    @DisplayName("Запрос на создание курьера без пароля")
    public void CreateCourierWithEmptyPassword() {
        courier.setPassword(null);
        var createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = createResponse.extract().path("message");

        Assert.assertEquals(expectedMessage, actualMessage);
        Assert.assertEquals(SC_BAD_REQUEST, statusCode);

    }

    @Test
    @DisplayName("Запрос на создание курьера без логина и пароля")
    public void CreateCourierWithEmptyCredentials() {
        courier.setLogin(null);
        courier.setPassword(null);
        var createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = createResponse.extract().path("message");

        Assert.assertEquals(expectedMessage, actualMessage);
        Assert.assertEquals(SC_BAD_REQUEST, statusCode);

    }

}
