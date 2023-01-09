package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.OrderItem;
import by.godevelopment.domain.models.ResultItem;
import by.godevelopment.testsources.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateOrderItemToResultItemTest extends BaseTest {

    private final CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase = new CalculateOrderItemToResultItemUseCase.BaseImpl();

    @Test
    void calculateTo_isCorrect() {
        OrderItem orderItem = orderItem_3_1;
        ResultItem actual = calculateOrderItemToResultItemUseCase.CalculateOrNull(orderItem);
        ResultItem expected = resultItem_3_1;
        assertEquals(expected, actual);
    }

    @Test
    void calculateTo_passQuantityMoreThan4_isCorrect() {
        OrderItem orderItem = orderItem_5_1;
        ResultItem actual = calculateOrderItemToResultItemUseCase.CalculateOrNull(orderItem);
        ResultItem expected = resultItem_5_1;
        assertEquals(expected, actual);
    }

    @Test
    void calculateTo_passNull_isCorrect() {
        OrderItem orderItem = null;
        ResultItem actual = calculateOrderItemToResultItemUseCase.CalculateOrNull(orderItem);
        ResultItem expected = null;
        assertEquals(expected, actual);
    }
}