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

    public OrderResult calculateOrderResultToOrderOrNull(Order order);

    class BaseImpl implements CalculateOrderToOrderResultUseCase {

        private final CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase;
        private BigDecimal totalWithDiscount;

        public BaseImpl(CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase) {
            this.calculateOrderItemToResultItemUseCase = calculateOrderItemToResultItemUseCase;
        }

        @Override
        public OrderResult calculateOrderResultToOrderOrNull(Order order) {
            List<ResultItem> resultItems = order.items().stream().map(calculateOrderItemToResultItemUseCase::CalculateTo).toList();

            BigDecimal total = BigDecimal.valueOf(0);
            for (ResultItem item: resultItems) {
                total = total.add(item.total());
            }
            total = total.setScale(ROUNDING_MODE_PLACES, RoundingMode.HALF_UP);

            DiscountsItem bestDiscount = order.discounts().stream().max(DiscountsItem::compare).orElse(new DiscountsItem(null, 0));
            if (bestDiscount.rate() > 0) {
                BigDecimal fraction = BigDecimal.valueOf(bestDiscount.rate());
                fraction = fraction.divide(BigDecimal.valueOf(FULL_PERCENT));
                fraction = BigDecimal.valueOf(1).subtract(fraction);
                totalWithDiscount = total.multiply(fraction);
                totalWithDiscount = totalWithDiscount.setScale(ROUNDING_MODE_PLACES, RoundingMode.HALF_UP);
            }
            return new OrderResult (
                    order.id(),
                    resultItems,
                    total,
                    bestDiscount,
                    totalWithDiscount
            );
        }
    }
}
