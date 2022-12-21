package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.OrderItem;
import by.godevelopment.domain.models.ResultItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface CalculateOrderItemToResultItemUseCase {

    int QUANTITY_DISCOUNT_LOW_LEVEL = 5;
    int DISCOUNT_PERCENT = 10;
    int FULL_PERCENT = 100;
    int ROUNDING_MODE_PLACES = 2;

    public ResultItem CalculateTo(OrderItem orderItem);

    class BaseImpl implements CalculateOrderItemToResultItemUseCase {
        @Override
        public ResultItem CalculateTo(OrderItem orderItem) {

            int quantity = orderItem.quantity();
            String name = orderItem.storeItem().name();
            BigDecimal price = orderItem.storeItem().price();
            BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));

            if (quantity >= QUANTITY_DISCOUNT_LOW_LEVEL) {
                BigDecimal fraction = BigDecimal.valueOf(DISCOUNT_PERCENT);
                fraction = fraction.divide(BigDecimal.valueOf(FULL_PERCENT));
                fraction = BigDecimal.valueOf(1).subtract(fraction);
                total = total.multiply(fraction);
                name = name + " with -10%";
            }
            total = total.setScale(ROUNDING_MODE_PLACES, RoundingMode.HALF_UP);
            return new ResultItem(
                    quantity,
                    name,
                    price,
                    total
            );
        }
    }
}
