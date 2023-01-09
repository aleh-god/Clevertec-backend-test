package by.godevelopment.domain.usecases;

import by.godevelopment.data.StoreItemsDataSource;
import by.godevelopment.domain.models.OrderItem;
import by.godevelopment.domain.models.StoreItem;

public interface ParseStringToOrderItemUseCase {

    String SEPARATOR = "-";

    public OrderItem parseStringToOrderItemOrNull(String input);

    class BaseImpl implements ParseStringToOrderItemUseCase {

        private final String separator;
        private final ValidateOrderItemIdInStoreUseCase validateUseCase;
        private final StoreItemsDataSource storeItemsDataSource;

        public BaseImpl(
                String separator,
                ValidateOrderItemIdInStoreUseCase validateUseCase,
                StoreItemsDataSource storeItemsDataSource
        ) {
            this.separator = separator;
            this.validateUseCase = validateUseCase;
            this.storeItemsDataSource = storeItemsDataSource;
        }

        @Override
        public OrderItem parseStringToOrderItemOrNull(String input) {
            try {
                if (input.length() > 2) {
                    String[] parts = input.split(separator);
                    if (parts.length == 2) {
                        int id = Integer.parseInt(parts[0]);
                        int quantity = Integer.parseInt(parts[1]);
                        if (validateUseCase.validateBy(id) && quantity > 0) {
                            StoreItem item = storeItemsDataSource.getStoreItemByIdOrNull(id);
                            return new OrderItem(item, quantity);
                        }
                        else return null;
                    } else return null;
                } else return null;
            } catch (Exception e) {
                System.out.println("mapStringToOrderItem catch: " + e.getMessage());
                return null;
            }
        }
    }
}
