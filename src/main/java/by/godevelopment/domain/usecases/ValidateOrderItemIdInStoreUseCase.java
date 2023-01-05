package by.godevelopment.domain.usecases;

import by.godevelopment.data.StoreItemsDataSource;

public interface ValidateOrderItemIdInStoreUseCase {

    public Boolean validateBy(int id);

    class BaseImpl implements ValidateOrderItemIdInStoreUseCase {

        private final StoreItemsDataSource storeItemsDataSource;

        public BaseImpl(StoreItemsDataSource storeItemsDataSource) {
            this.storeItemsDataSource = storeItemsDataSource;
        }

        @Override
        public Boolean validateBy(int id) {
            return storeItemsDataSource.contains(id);
        }
    }
}
