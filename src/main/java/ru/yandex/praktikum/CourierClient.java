package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {
    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Вход по логину в систему с валидными данными")
    public int login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

    }

    @Step("Вход по логину с невалидными данными")
    public ValidatableResponse loginIsNotValid (CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();

    }

    @Step("Удаление курьера")
    public boolean delete(int id) {
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + id)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");

    }
}
