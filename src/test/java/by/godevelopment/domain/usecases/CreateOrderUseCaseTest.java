package by.godevelopment.domain.usecases;

import by.godevelopment.data.DiscountItemsDataSource;
import by.godevelopment.data.StringDataSource;
import by.godevelopment.domain.models.DiscountsItem;
import by.godevelopment.domain.models.Order;
import by.godevelopment.domain.models.OrderItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderUseCaseTest extends BaseUseCaseTest {

    DiscountItemsDataSource discountItemsDataSource = new DiscountItemsDataSource.BaseImpl();
    CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase.BaseImpl(
            parseStringToOrderItemUseCase,
            discountItemsDataSource
    );
    StringDataSource stringDataSource = new StringDataSource.BaseImpl();

    @Test
    public void createOrder_isCorrect() {

        Order actual = createOrderUseCase.createOrderOrReturnNull(
                stringDataSource.getInputDataOrNull()
        );

        List<DiscountsItem> discounts = new ArrayList<>();
        discounts.add(
                discountItemsDataSource.getDiscountsItemByStringOrNull("card-1234")
        );

        List<OrderItem> items = new ArrayList<>();
        items.add(
                new OrderItem(storeItemsDataSource.getStoreItemByIdOrNull(3),1)
        );
        items.add(
                new OrderItem(storeItemsDataSource.getStoreItemByIdOrNull(2),5)
        );
        items.add(
                new OrderItem(storeItemsDataSource.getStoreItemByIdOrNull(5),1)
        );

        Order expected = new Order(
                1,
                discounts,
                items
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void createOrder_withoutDiscounts_isCorrect() {

        Order actual = createOrderUseCase.createOrderOrReturnNull(
                "3-1 2-5 5-1"
        );

        List<DiscountsItem> discounts = new ArrayList<>();

        List<OrderItem> items = new ArrayList<>();
        items.add(
                new OrderItem(storeItemsDataSource.getStoreItemByIdOrNull(3),1)
        );
        items.add(
                new OrderItem(storeItemsDataSource.getStoreItemByIdOrNull(2),5)
        );
        items.add(
                new OrderItem(storeItemsDataSource.getStoreItemByIdOrNull(5),1)
        );

        Order expected = new Order(
                1,
                discounts,
                items
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void createOrder_passNull_returnNul() {

        Order actual = createOrderUseCase.createOrderOrReturnNull(
                null
        );

        Assertions.assertNull(actual);
    }

    @Test
    public void createOrder_passBadString_returnNull() {

        Order actual = createOrderUseCase.createOrderOrReturnNull(
                "Hello card-1234"
        );

        Assertions.assertNull(actual);
    }
}
