package by.godevelopment.view;

import by.godevelopment.domain.models.OrderResult;
import by.godevelopment.domain.models.Receipt;
import by.godevelopment.testsources.BaseTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static by.godevelopment.domain.usecases.CalculateOrderToOrderResultUseCase.nullObjectDiscountsItem;
import static by.godevelopment.view.OrderResultToReceiptBehavior.ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderResultToReceiptBehaviorTest extends BaseTest {

    OrderResultToReceiptBehavior orderResultToReceiptBehavior = new OrderResultToReceiptBehavior.BaseImpl();

    @Test
    void mapOrNull_passNull_returnNull() {

        Receipt actual = orderResultToReceiptBehavior.invoke(null);
        Receipt expected = new Receipt(
                -1,
                List.of(ERROR_MESSAGE)
        );
        assertEquals(expected, actual);
    }

    @Test
    void mapOrNull_OrderResultWithoutDiscount_returnReceipt() {

        OrderResult orderResult_withoutDiscount = new OrderResult(
                1,
                resultItems_1,
                BigDecimal.valueOf(3.84), // 1.25 + 2.25 + 0.34 -> 3.84
                nullObjectDiscountsItem,
                BigDecimal.valueOf(3.84)
        );

        Receipt actual = orderResultToReceiptBehavior.invoke(orderResult_withoutDiscount);
        Receipt expected = new Receipt(
                1,
                List.of("Receipt #1",
                        "QTY  Description  Price  Total",
                        "1  Muesli  1.25  1.25", "5  Fresh milk with -10%  0.5  2.25", "1  Potatoes  0.34  0.34",
                        "##############################",
                        "Total: 3.84",
                        "Total with discount: 3.84")
        );

        assertEquals(expected, actual);
    }

    @Test
    void mapOrNull_OrderResultWithDiscount_returnReceipt() {

        OrderResult orderResult_withDiscount = new OrderResult(
                1,
                resultItems_1,
                BigDecimal.valueOf(3.84), // 1.25 + 2.25 + 0.34 -> 3.84
                discountsItem_1234, // 15%
                BigDecimal.valueOf(3.26)
        );

        Receipt actual = orderResultToReceiptBehavior.invoke(orderResult_withDiscount);
        Receipt expected = new Receipt(
                1,
                List.of("Receipt #1",
                        "QTY  Description  Price  Total",
                        "1  Muesli  1.25  1.25", "5  Fresh milk with -10%  0.5  2.25", "1  Potatoes  0.34  0.34",
                        "##############################",
                        "Total: 3.84",
                        "Discount: card-1234  15%",
                        "Total with discount: 3.26")
        );

        assertEquals(expected, actual);
    }
}
