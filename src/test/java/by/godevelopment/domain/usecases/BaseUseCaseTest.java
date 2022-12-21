package by.godevelopment.domain.usecases;

import by.godevelopment.data.StoreItemsDataSource;

import java.util.List;

public class BaseUseCaseTest {

    StoreItemsDataSource storeItemsDataSource = new StoreItemsDataSource.BaseImpl();
    List<Integer> ids = storeItemsDataSource.getStoreItemsId();
    ParseStringToOrderItemUseCase parseStringToOrderItemUseCase = new ParseStringToOrderItemUseCase.BaseImpl(
            ParseStringToOrderItemUseCase.SEPARATOR,
            new ValidateOrderItemIdInStoreUseCase.BaseImpl(ids),
            new StoreItemsDataSource.BaseImpl()
    );
}
