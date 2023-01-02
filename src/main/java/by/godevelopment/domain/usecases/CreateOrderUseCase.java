package by.godevelopment.domain.usecases;

import by.godevelopment.data.DiscountItemsDataSource;
import by.godevelopment.domain.models.DiscountsItem;
import by.godevelopment.domain.models.Order;
import by.godevelopment.domain.models.OrderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static by.godevelopment.constants.AppConfig.CARD_LABEL;
import static by.godevelopment.constants.AppConfig.PARTS_DIVIDER;

public interface CreateOrderUseCase {

    public Order createOrderOrReturnNull(String inputData);

    class BaseImpl implements CreateOrderUseCase {

        private int orderIdState;
        private final String divider;
        private final ParseStringToOrderItemUseCase parseStringToOrderItemUseCase;
        private final DiscountItemsDataSource discountItemsDataSource;

        public BaseImpl(
                ParseStringToOrderItemUseCase parseStringToOrderItemUseCase,
                        DiscountItemsDataSource discountItemsDataSource
        ) {
            this.parseStringToOrderItemUseCase = parseStringToOrderItemUseCase;
            this.discountItemsDataSource = discountItemsDataSource;
            orderIdState = 0;
            divider = PARTS_DIVIDER;
        }

        public BaseImpl(
                int lastOrderIdState,
                String customDivider,
                ParseStringToOrderItemUseCase parseStringToOrderItemUseCase,
                DiscountItemsDataSource discountItemsDataSource
        ) {
            this.orderIdState = lastOrderIdState;
            this.divider = customDivider;
            this.parseStringToOrderItemUseCase = parseStringToOrderItemUseCase;
            this.discountItemsDataSource = discountItemsDataSource;
        }

        @Override
        public Order createOrderOrReturnNull(String inputData) {
            List<String> dataParts = splitInputData(inputData);
            if (dataParts.isEmpty()) { return null; }
            else {
                List<DiscountsItem> discounts = new ArrayList<>();
                List<OrderItem> items = new ArrayList<>();
                for (String part : dataParts) {
                    if (part.contains(CARD_LABEL)) {
                        DiscountsItem itemOrNull = discountItemsDataSource.getDiscountsItemByStringOrNull(part);
                        if (itemOrNull != null) discounts.add(itemOrNull);
                    }
                    else {
                        OrderItem itemOrNull = parseStringToOrderItemUseCase.parseStringToOrderItemOrNull(part);
                        if (itemOrNull != null) items.add(itemOrNull);
                    }
                }
                if (!items.isEmpty()) return new Order(
                        ++orderIdState,
                        discounts,
                        items
                );
                else return null;
            }
        }

        private List<String> splitInputData(String inputData) {
            if (inputData !=null && !inputData.isEmpty() && inputData.contains(divider)) return List.of(inputData.split(divider));
            else return Collections.emptyList();
        }
    }
}
