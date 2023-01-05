package by.godevelopment.di;

import by.godevelopment.data.StringDataSource;
import by.godevelopment.domain.usecases.*;
import by.godevelopment.view.OrderResultToReceiptBehavior;
import by.godevelopment.view.PrintReceiptBehaviour;

public interface ServiceLocator {
    StringDataSource provideStringDataSource();
    ValidateOrderItemIdInStoreUseCase provideValidateOrderItemIdInStoreUseCase();
    ParseStringToOrderItemUseCase provideParseStringToOrderItemUseCase();
    CreateOrderUseCase provideCreateOrderUseCase();
    CalculateOrderItemToResultItemUseCase provideCalculateOrderItemToResultItemUseCase();
    CalculateOrderToOrderResultUseCase provideCalculateOrderToOrderResultUseCase();
    OrderResultToReceiptBehavior provideOrderResultToReceiptBehavior();
    PrintReceiptBehaviour providePrintReceiptBehaviour();
}
