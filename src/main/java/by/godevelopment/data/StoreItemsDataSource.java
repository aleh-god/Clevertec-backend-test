package by.godevelopment.data;

import by.godevelopment.domain.models.StoreItem;

import java.math.BigDecimal;
import java.util.HashMap;

public interface StoreItemsDataSource {

    public StoreItem getStoreItemByIdOrNull(int id);

    Boolean contains(int id);

    class BaseImpl implements StoreItemsDataSource {

        private final HashMap<Integer, StoreItem> storeItems;

        public BaseImpl() {
            this.storeItems = new HashMap<Integer, StoreItem>();
            storeItems.put(1, new StoreItem(1, "Coca-cola", BigDecimal.valueOf(1.00)));
            storeItems.put(2, new StoreItem(2, "Fresh milk", BigDecimal.valueOf(0.50)));
            storeItems.put(3, new StoreItem(3, "Muesli", BigDecimal.valueOf(1.25)));
            storeItems.put(4, new StoreItem(4, "Pork leg steak", BigDecimal.valueOf(2.99)));
            storeItems.put(5, new StoreItem(5, "Potatoes", BigDecimal.valueOf(0.34)));
        }

        @Override
        public StoreItem getStoreItemByIdOrNull(int id) {
            return storeItems.get(id);
        }

        @Override
        public Boolean contains(int id) {
            return storeItems.containsKey(id);
        }
    }
}
