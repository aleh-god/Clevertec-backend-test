package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.OrderItem;
import by.godevelopment.domain.models.ResultItem;
import by.godevelopment.domain.models.StoreItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculateOrderItemToResultItemUseCaseTest {

    private final CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase = new CalculateOrderItemToResultItemUseCase.BaseImpl();
    private final StoreItem storeItem = new StoreItem(1, "Coca-cola", BigDecimal.valueOf(1.49));

    @Test
    void calculateTo_isCorrect() {
        OrderItem orderItem = new OrderItem(storeItem, 4);
        ResultItem actual = calculateOrderItemToResultItemUseCase.CalculateTo(orderItem);
        ResultItem expected = new ResultItem(
                4,
                "Coca-cola",
                BigDecimal.valueOf(1.49),
                BigDecimal.valueOf(5.96)
        );
        assertEquals(expected, actual);
    }

    @Test
    void calculateTo_passQuantityMoreThan4_isCorrect() {
        OrderItem orderItem = new OrderItem(storeItem, 5);
        ResultItem actual = calculateOrderItemToResultItemUseCase.CalculateTo(orderItem);
        ResultItem expected = new ResultItem(
                5,
                "Coca-cola with -10%",
                BigDecimal.valueOf(1.49),
                BigDecimal.valueOf(6.71)    // 7.45 -> 6.705 -> 6.71
        );
        assertEquals(expected, actual);
    }

    @Test
    void calculateTo_passQuantityMoreThan5_isCorrect() {
        OrderItem orderItem = new OrderItem(storeItem, 6);
        ResultItem actual = calculateOrderItemToResultItemUseCase.CalculateTo(orderItem);
        ResultItem expected = new ResultItem(
                6,
                "Coca-cola with -10%",
                BigDecimal.valueOf(1.49),
                BigDecimal.valueOf(8.05)    // 8.94 -> 8.046 -> 8.05
        );
        assertEquals(expected, actual);
    }
}