package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateOrderToOrderResultUseCaseTest {

    private final StoreItem storeItem = new StoreItem(1, "Coca-cola", BigDecimal.valueOf(1.49));
    OrderItem orderItem = new OrderItem(storeItem, 4);

    DiscountsItem discountsItem = new DiscountsItem("card-1234", 15);

    ResultItem resultItem = new ResultItem(
            4,
            "Coca-cola",
            BigDecimal.valueOf(1.49),
            BigDecimal.valueOf(5.96)
    );
    List<DiscountsItem> discounts = new ArrayList<>();
    List<OrderItem> items = new ArrayList<>();
    List<ResultItem> resultItems = new ArrayList<>();
    private final CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase =
            new CalculateOrderItemToResultItemUseCase.BaseImpl();
    private final CalculateOrderToOrderResultUseCase calculateOrderToOrderResultUseCase =
            new CalculateOrderToOrderResultUseCase.BaseImpl(
                    calculateOrderItemToResultItemUseCase
            );
    @Test
    void calculateReceiptByOrderOrNull() {

        discounts.add(discountsItem);
        items.add(orderItem);
        resultItems.add(resultItem);

        Order order = new Order(
                1,
                discounts,
                items
        );
        OrderResult actual = calculateOrderToOrderResultUseCase.calculateOrderResultToOrderOrNull(order);
        OrderResult expected = new OrderResult(
                1,
                resultItems,
                BigDecimal.valueOf(5.96),
                discountsItem,
                BigDecimal.valueOf(5.07)
        );
        assertEquals(expected, actual);
    }
}