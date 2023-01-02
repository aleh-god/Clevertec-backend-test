package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.DiscountsItem;
import by.godevelopment.domain.models.Order;
import by.godevelopment.testsources.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CreateOrderTest extends BaseTest {
    CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase.BaseImpl(
            parseStringToOrderItemUseCase,
            discountItemsDataSource
    );

    @BeforeEach
    public void initTest() {
    }

    @Test
    public void createOrder_isCorrect() {

        Order actual = createOrderUseCase.createOrderOrReturnNull("3-1 2-5 5-1 card-1234");
        Order expected = order_withDiscount_1234;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void createOrder_withoutDiscounts_isCorrect() {

        Order actual = createOrderUseCase.createOrderOrReturnNull("3-1 2-5 5-1");
        Order expected = new Order(
                1,
                new ArrayList<DiscountsItem>(),
                items_1
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void createOrder_passNull_returnNul() {

        Order actual = createOrderUseCase.createOrderOrReturnNull(null);

        Assertions.assertNull(actual);
    }

    @Test
    public void createOrder_passBadString_returnNull() {

        Order actual = createOrderUseCase.createOrderOrReturnNull("Hello card-1234");

        Assertions.assertNull(actual);
    }
}
