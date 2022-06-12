package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

@Story("Создание курьера с не валидными учетными данными")
public class CreatingCourierAndLoggingIinWithInvalidDataTest {
    private CourierClient courierClient;
    private Courier courier;
    private int statusCode;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @Test
    @Description("Создание курьера c помощью рандомного генератора, с целью проверки создания курьера с повторяющим логином")
    @DisplayName("Создание курьера с повторяющимся логином")
    public void createCourierWithDuplicateCredentials() {
        var createFirstCourier = courierClient.create(courier);
        var createSecondCourier = courierClient.create(courier);
        statusCode = createSecondCourier.extract().statusCode();
        String expectedMessage = "Этот логин уже используется. Попробуйте другой.";
        String actualMessage = createSecondCourier.extract().path("message");

        Assert.assertEquals(expectedMessage, actualMessage);
        Assert.assertEquals(SC_CONFLICT, statusCode);
    }

    @Test
    @Description("Создание курьера с нулевым значением в поле ввода login")
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
    @Description("Создание курьера с нулевым значением в поле ввода password")
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
    @Description("Создание курьера с нулевым значением в поле ввода login и password")
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

    @Test
    @Description("Вход в систему с пустым значением в поле ввода login")
    @DisplayName("Запрос без логина")
    public void courierLoginWithEmptyLogin() {
        ValidatableResponse LoginResponse = courierClient.loginIsNotValid(new CourierCredentials("", courier.getPassword()));
        statusCode = LoginResponse.extract().statusCode();
        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = LoginResponse.extract().path("message");

        Assert.assertEquals(SC_BAD_REQUEST, statusCode);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Description("Вход в систему с пустым значением в поле ввода password")
    @DisplayName("Запрос без пароля")
    public void courierLoginWithEmptyPassword() {
        ValidatableResponse LoginResponse = courierClient.loginIsNotValid(new CourierCredentials(courier.getLogin(), ""));
        statusCode = LoginResponse.extract().statusCode();
        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = LoginResponse.extract().path("message");

        Assert.assertEquals(SC_BAD_REQUEST, statusCode);
        Assert.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @Description("Вход в систему с пустым значением в поле ввода login и password")
    @DisplayName("Запрос без логина и пароля")
    public void courierLoginWithEmptyLoginPassword() {
        ValidatableResponse LoginResponse = courierClient.loginIsNotValid(new CourierCredentials("", ""));
        statusCode = LoginResponse.extract().statusCode();
        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = LoginResponse.extract().path("message");

        Assert.assertEquals(SC_BAD_REQUEST, statusCode);
        Assert.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @Description("Вход в систему с несуществующим значением в поле ввода login")
    @DisplayName("Запрос с несуществующим логином")
    public void CourierLoginWithNonExistentLogin() {
        ValidatableResponse loginResponse = courierClient.loginIsNotValid(new CourierCredentials(courier.getLogin().concat("vfhujifvfhujif"), courier.getPassword()));
        statusCode = loginResponse.extract().statusCode();
        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = loginResponse.extract().path("message");

        Assert.assertEquals(SC_NOT_FOUND, statusCode);
        Assert.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @Description("Вход в систему с несуществующим значением в поле ввода password")
    @DisplayName("Запрос с несуществующим паролем")
    public void CourierLoginWithNonExistentPassword() {
        ValidatableResponse loginResponse = courierClient.loginIsNotValid(new CourierCredentials(courier.getLogin(), courier.getPassword().concat("vfhujif2022")));
        statusCode = loginResponse.extract().statusCode();
        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = loginResponse.extract().path("message");

        Assert.assertEquals(SC_NOT_FOUND, statusCode);
        Assert.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @Description("Вход в систему с несуществующим значением в поле ввода login и password")
    @DisplayName("Запрос с несуществующей парой логином-пароль")
    public void CourierLoginWithNonExistentLoginPassword() {
        ValidatableResponse loginResponse = courierClient.loginIsNotValid(new CourierCredentials(courier.getLogin().concat("vfhbghjbvujif"), courier.getPassword().concat("vfhujif202213")));
        statusCode = loginResponse.extract().statusCode();
        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = loginResponse.extract().path("message");

        Assert.assertEquals(SC_NOT_FOUND, statusCode);
        Assert.assertEquals(expectedMessage, actualMessage);

    }


}
