package ru.yandex.praktikum;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;

@Story("Список заказов курьера")
public class OrderListTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получить список заказов клиента")
    public void getOrderList() {
        var listOfOrdersResponse = orderClient.getOrderlist();
        int statusCode = listOfOrdersResponse.extract().statusCode();
        int ordersCount = listOfOrdersResponse.extract().path("pageInfo.total");
        ArrayList<Integer> ordersId = listOfOrdersResponse.extract().path("orders.id");
        ArrayList<String> availableStationsNames = listOfOrdersResponse.extract().path("availableStations.name");

        Assert.assertEquals(SC_OK, statusCode);
        Assert.assertNotEquals(0, ordersCount);
        Assert.assertNotEquals(0, ordersId.size());
        Assert.assertNotEquals(0, availableStationsNames.size());
    }

}
