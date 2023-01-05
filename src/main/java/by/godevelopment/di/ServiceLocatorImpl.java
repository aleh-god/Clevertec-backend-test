package by.godevelopment.di;

import by.godevelopment.data.DiscountItemsDataSource;
import by.godevelopment.data.StoreItemsDataSource;
import by.godevelopment.data.StringDataSource;
import by.godevelopment.domain.usecases.*;
import by.godevelopment.view.OrderResultToReceiptBehavior;
import by.godevelopment.view.PrintReceiptBehaviour;

public class ServiceLocatorImpl implements ServiceLocator {
    StoreItemsDataSource storeItemsDataSource;
    DiscountItemsDataSource discountItemsDataSource;
    StringDataSource stringDataSource;
    PrintReceiptBehaviour printReceiptBehaviour;

    public ServiceLocatorImpl(
            String fileNameIn,
            String fileNameOut
    ) {
        if (fileNameIn != null && fileNameOut != null) {
            stringDataSource = new StringDataSource.ReadFileImpl(fileNameIn);
            printReceiptBehaviour = new PrintReceiptBehaviour.WriteFileImpl(fileNameOut);
        }
        else {
            stringDataSource = new StringDataSource.ReadFileImpl();
            printReceiptBehaviour = new PrintReceiptBehaviour.WriteFileImpl();
        }

        storeItemsDataSource = new StoreItemsDataSource.BaseImpl();
        discountItemsDataSource = new DiscountItemsDataSource.BaseImpl();
    }

    @Override
    public StringDataSource provideStringDataSource() {
        return stringDataSource;
    }

    @Override
    public ValidateOrderItemIdInStoreUseCase provideValidateOrderItemIdInStoreUseCase() {
        return new ValidateOrderItemIdInStoreUseCase.BaseImpl(storeItemsDataSource);
    }

    @Override
    public ParseStringToOrderItemUseCase provideParseStringToOrderItemUseCase() {
        return new ParseStringToOrderItemUseCase.BaseImpl(
                ParseStringToOrderItemUseCase.SEPARATOR,
                provideValidateOrderItemIdInStoreUseCase(),
                storeItemsDataSource
        );
    }

    @Override
    public CreateOrderUseCase provideCreateOrderUseCase() {
        return new CreateOrderUseCase.BaseImpl(
                provideParseStringToOrderItemUseCase(),
                discountItemsDataSource
        );
    }

    @Override
    public CalculateOrderItemToResultItemUseCase provideCalculateOrderItemToResultItemUseCase() {
        return new CalculateOrderItemToResultItemUseCase.BaseImpl();
    }

    @Override
    public CalculateOrderToOrderResultUseCase provideCalculateOrderToOrderResultUseCase() {
        return new CalculateOrderToOrderResultUseCase.BaseImpl(
                provideCalculateOrderItemToResultItemUseCase()
        );
    }

    @Override
    public OrderResultToReceiptBehavior provideOrderResultToReceiptBehavior() {
        return new OrderResultToReceiptBehavior.BaseImpl();
    }

    @Override
    public PrintReceiptBehaviour providePrintReceiptBehaviour() {
        return printReceiptBehaviour;
    }
}
