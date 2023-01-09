package by.godevelopment.domain.usecases;

import by.godevelopment.domain.models.DiscountsItem;
import by.godevelopment.domain.models.Order;
import by.godevelopment.domain.models.OrderResult;
import by.godevelopment.domain.models.ResultItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public interface CalculateOrderToOrderResultUseCase {

    int ROUNDING_MODE_PLACES = 2;

    int FULL_PERCENT = 100;

    public final DiscountsItem nullObjectDiscountsItem = new DiscountsItem("", 0);

    public OrderResult calculateOrNull(Order order);

    class BaseImpl implements CalculateOrderToOrderResultUseCase {

        private final CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase;

        public BaseImpl(CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase) {
            this.calculateOrderItemToResultItemUseCase = calculateOrderItemToResultItemUseCase;
        }

        @Override
        public OrderResult calculateOrNull(Order order) {

            if (order != null) {
                List<ResultItem> resultItems = order
                        .items()
                        .stream()
                        .map(calculateOrderItemToResultItemUseCase::CalculateOrNull)
                        .toList();

                BigDecimal total = BigDecimal.valueOf(0);
                for (ResultItem item : resultItems) {
                    total = total.add(item.total());
                }
                total = total.setScale(ROUNDING_MODE_PLACES, RoundingMode.HALF_UP);

                DiscountsItem bestDiscount = order.discounts()
                        .stream().max(DiscountsItem::compare).orElse(nullObjectDiscountsItem);
                BigDecimal totalWithDiscount = null;
                if (bestDiscount.rate() > 0) {
                    BigDecimal fraction = BigDecimal.valueOf(bestDiscount.rate());
                    fraction = fraction.divide(BigDecimal.valueOf(FULL_PERCENT));
                    fraction = BigDecimal.valueOf(1).subtract(fraction);
                    totalWithDiscount = total.multiply(fraction);
                    totalWithDiscount = totalWithDiscount.setScale(ROUNDING_MODE_PLACES, RoundingMode.HALF_UP);
                }
                else totalWithDiscount = total;

                return new OrderResult(
                        order.id(),
                        resultItems,
                        total,
                        bestDiscount,
                        totalWithDiscount
                );
            }
            else return null;
        }
    }
}
