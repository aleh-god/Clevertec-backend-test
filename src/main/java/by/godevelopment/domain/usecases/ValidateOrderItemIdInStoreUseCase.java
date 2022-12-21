package by.godevelopment.domain.usecases;

import java.util.List;

public interface ValidateOrderItemIdInStoreUseCase {

    public Boolean validateBy(int id);

    class BaseImpl implements ValidateOrderItemIdInStoreUseCase {

        private final List<Integer> storeItemsId;

        public BaseImpl(List<Integer> storeItemsId) {
            this.storeItemsId = storeItemsId;
        }

        @Override
        public Boolean validateBy(int id) {
            return storeItemsId.contains(id);
        }
    }
}
