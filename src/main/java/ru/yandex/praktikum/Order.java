package ru.yandex.praktikum;

import java.util.List;

public class Order {
    private final String firstName = "Вячеслав";
    private final String lastName = "Жуков";
    private final String address = "Комсомольский проспект, 10";
    private final String metroStation = "Сокольники";
    private final String phone = "+375296264335";
    private final String rentTime = "2";
    private final String deliveryDate = "30-06-2022";
    private final String comment = "Я учусь тестировать API";
    private List<String> color;

    public Order() {
    }

    public Order(List<String> color) {
        this.color = color;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }
}
