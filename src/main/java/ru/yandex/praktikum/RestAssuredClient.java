package ru.yandex.praktikum;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RestAssuredClient {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .setContentType(JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

}
