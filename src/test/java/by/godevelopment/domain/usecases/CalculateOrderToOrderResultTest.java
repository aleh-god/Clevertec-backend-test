package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.*;
import by.godevelopment.testsources.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static by.godevelopment.domain.usecases.CalculateOrderToOrderResultUseCase.nullObjectDiscountsItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CalculateOrderToOrderResultTest extends BaseTest {
    private final CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase =
            new CalculateOrderItemToResultItemUseCase.BaseImpl();
    private final CalculateOrderToOrderResultUseCase calculateOrderToOrderResultUseCase =
            new CalculateOrderToOrderResultUseCase.BaseImpl(
                    calculateOrderItemToResultItemUseCase
            );

    @BeforeEach
    void initTest() {
    }
    @Test
    void calculateReceiptByOrderOrNull_withDiscount_isCorrect() {

        Order order = new Order(
                1,
                discounts_1,
                items_1
        );
        OrderResult actual = calculateOrderToOrderResultUseCase.calculateOrNull(order);
        OrderResult expected = new OrderResult(
                1,
                resultItems_1,
                BigDecimal.valueOf(3.84), // 1.25 + 2.25 + 0.34 -> 3.84
                discountsItem_1234, // 15%
                BigDecimal.valueOf(3.26)
        );

        assertEquals(expected, actual);
    }

    @Test
    void calculateReceiptByOrderOrNull_withoutDiscount_isCorrect() {

        Order order = new Order(
                1,
                new ArrayList<>(),
                items_1
        );
        OrderResult actual = calculateOrderToOrderResultUseCase.calculateOrNull(order);
        OrderResult expected = new OrderResult(
                1,
                resultItems_1,
                BigDecimal.valueOf(3.84), // 1.25 + 2.25 + 0.34 -> 3.84
                nullObjectDiscountsItem,
                BigDecimal.valueOf(3.84)
        );

        assertEquals(expected, actual);
    }

    @Test
    void calculateReceiptByOrderOrNull_passNull_returnNull() {

        Order order = null;
        OrderResult actual = calculateOrderToOrderResultUseCase.calculateOrNull(order);

        assertNull(actual);
    }
}
