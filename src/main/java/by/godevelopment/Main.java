package by.godevelopment;

import by.godevelopment.data.DiscountItemsDataSource;
import by.godevelopment.data.StoreItemsDataSource;
import by.godevelopment.data.StringDataSource;
import by.godevelopment.domain.models.Order;
import by.godevelopment.domain.models.OrderResult;
import by.godevelopment.domain.models.Receipt;
import by.godevelopment.domain.usecases.*;
import by.godevelopment.view.OrderResultToReceiptBehavior;
import by.godevelopment.view.PrintReceiptBehaviour;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        StoreItemsDataSource storeItemsDataSource = new StoreItemsDataSource.BaseImpl();
        List<Integer> storeItemsId = storeItemsDataSource.getStoreItemsId();
        ValidateOrderItemIdInStoreUseCase validateUseCase = new ValidateOrderItemIdInStoreUseCase.BaseImpl(storeItemsId);


        ParseStringToOrderItemUseCase parseStringToOrderItemUseCase = new ParseStringToOrderItemUseCase.BaseImpl(
                ParseStringToOrderItemUseCase.SEPARATOR,
                validateUseCase,
                storeItemsDataSource
        );
        DiscountItemsDataSource discountItemsDataSource =  new DiscountItemsDataSource.BaseImpl();

        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase.BaseImpl(
                parseStringToOrderItemUseCase,
                discountItemsDataSource
        );

        String inputData = new StringDataSource.BaseImpl().getInputDataOrNull();
        Order order = createOrderUseCase.createOrderOrReturnNull(inputData);

        CalculateOrderItemToResultItemUseCase calculateOrderItemToResultItemUseCase = new CalculateOrderItemToResultItemUseCase.BaseImpl();
        CalculateOrderToOrderResultUseCase calculateOrderToOrderResultUseCase = new CalculateOrderToOrderResultUseCase.BaseImpl(calculateOrderItemToResultItemUseCase);
        OrderResult orderResult = calculateOrderToOrderResultUseCase.calculateOrderResultToOrderOrNull(order);

        OrderResultToReceiptBehavior orderResultToReceiptBehavior = new OrderResultToReceiptBehavior.BaseImpl();
        Receipt receipt = orderResultToReceiptBehavior.invoke(orderResult);

        PrintReceiptBehaviour printReceiptBehaviour = new PrintReceiptBehaviour.BaseImpl();
        printReceiptBehaviour.printReceipt(receipt);
    }
}