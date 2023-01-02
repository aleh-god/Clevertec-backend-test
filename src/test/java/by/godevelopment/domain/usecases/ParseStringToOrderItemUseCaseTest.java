package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.OrderItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParseStringToOrderItemUseCaseTest extends BaseUseCaseTest {

    @Test
    public void parseStringToOrderItem_IsCorrect() {
        OrderItem expected = orderItem_2_5;
        OrderItem actual = parseStringToOrderItemUseCase.parseStringToOrderItemOrNull("2-5");
        assertEquals(expected, actual);
    }

    @Test
    public void parseStringToOrderItem_inputZeroQuantity_ReturnNull() {
        OrderItem actual = parseStringToOrderItemUseCase.parseStringToOrderItemOrNull("2-0");
        assertNull(actual);
    }

    @Test
    public void parseStringToOrderItem_inputWrongString_ReturnNull() {
        OrderItem actual = parseStringToOrderItemUseCase.parseStringToOrderItemOrNull("one-two");
        assertNull(actual);
    }

    @Test
    public void parseStringToOrderItem_inputWrongData1_ReturnNull() {
        OrderItem actual = parseStringToOrderItemUseCase.parseStringToOrderItemOrNull("1+2");
        assertNull(actual);
    }

    @Test
    public void parseStringToOrderItem_inputWrongData2_ReturnNull() {
        OrderItem actual = parseStringToOrderItemUseCase.parseStringToOrderItemOrNull("-1-2");
        assertNull(actual);
    }

    @Test
    public void parseStringToOrderItem_inputWrongData3_ReturnNull() {
        OrderItem actual = parseStringToOrderItemUseCase.parseStringToOrderItemOrNull("0-0");
        assertNull(actual);
    }
}
