package by.godevelopment.data;

import by.godevelopment.domain.models.StoreItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface StoreItemsDataSource {

    public List<StoreItem> getStoreItems();

    public List<Integer> getStoreItemsId();

    public StoreItem getStoreItemByIdOrNull(int id);

    class BaseImpl implements StoreItemsDataSource {

        private final HashSet<StoreItem> storeItems;

        public BaseImpl() {
            this.storeItems = new HashSet<StoreItem>();
            storeItems.add(new StoreItem(1, "Coca-cola", BigDecimal.valueOf(1.00)));
            storeItems.add(new StoreItem(2, "Fresh milk", BigDecimal.valueOf(0.50)));
            storeItems.add(new StoreItem(3, "Muesli", BigDecimal.valueOf(1.25)));
            storeItems.add(new StoreItem(4, "Pork leg steak", BigDecimal.valueOf(2.99)));
            storeItems.add(new StoreItem(5, "Potatoes", BigDecimal.valueOf(0.34)));
        }

        public BaseImpl(HashSet<StoreItem> inputItems) {
            this.storeItems = new HashSet<StoreItem>(inputItems);
        }

        @Override
        public List<StoreItem> getStoreItems() {
            return new ArrayList<>(storeItems);
        }

        @Override
        public List<Integer> getStoreItemsId() {
            return storeItems.stream().map(StoreItem::id).toList();
        }

        @Override
        public StoreItem getStoreItemByIdOrNull(int id) {
            for (StoreItem storeItem : storeItems) {
                if (storeItem.id() == id) return storeItem;
            }
            return null;
        }
    }
}
