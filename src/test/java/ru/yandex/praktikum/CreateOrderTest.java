package ru.yandex.praktikum;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;

@Story("Creating an order by a user")
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private Order order;
    private Integer track;
    private final List<String> scooterColors;

    public CreateOrderTest(String scooterColors) {
        this.scooterColors = List.of(scooterColors);
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {"\"BLACK\", \"GREY\""},
                {"\"GREY\""},
                {"\"BLACK\""},
                {""}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = new Order(scooterColors);
    }

    @After
    public void tearDown() {
        orderClient.cancelOrder(track.toString());
    }

    @Test
    @DisplayName("Создание ордера")
    public void createOrder() {
        var createResponse = orderClient.createOrder(order);
        int statusCode = createResponse.extract().statusCode();
        track = createResponse.extract().path("track");

        Assert.assertEquals(SC_CREATED, statusCode);
        Assert.assertNotEquals(0, track.intValue());

    }

}
