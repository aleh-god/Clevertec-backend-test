package by.godevelopment.testsources;

import by.godevelopment.data.DiscountItemsDataSource;
import by.godevelopment.data.StoreItemsDataSource;
import by.godevelopment.domain.models.*;
import by.godevelopment.domain.usecases.ParseStringToOrderItemUseCase;
import by.godevelopment.domain.usecases.ValidateOrderItemIdInStoreUseCase;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static by.godevelopment.domain.usecases.CalculateOrderItemToResultItemUseCase.DISCOUNT_DESCR;

public class BaseTest {

    protected DiscountItemsDataSource discountItemsDataSource = new DiscountItemsDataSource.BaseImpl();

    protected StoreItemsDataSource storeItemsDataSource = new StoreItemsDataSource.BaseImpl();
    protected ParseStringToOrderItemUseCase parseStringToOrderItemUseCase = new ParseStringToOrderItemUseCase.BaseImpl(
            ParseStringToOrderItemUseCase.SEPARATOR,
            new ValidateOrderItemIdInStoreUseCase.BaseImpl(storeItemsDataSource),
            new StoreItemsDataSource.BaseImpl()
    );

    // 3-1 2-5 5-1 card-1234
    protected DiscountsItem discountsItem_1234 = discountItemsDataSource.getDiscountsItemByStringOrNull("card-1234");
    protected StoreItem storeItem_3 = storeItemsDataSource.getStoreItemByIdOrNull(3);
    protected StoreItem storeItem_2 = storeItemsDataSource.getStoreItemByIdOrNull(2);
    protected StoreItem storeItem_5 = storeItemsDataSource.getStoreItemByIdOrNull(5);
    protected OrderItem orderItem_3_1 = new OrderItem(storeItem_3, 1);
    protected OrderItem orderItem_2_5 = new OrderItem(storeItem_2, 5);
    protected OrderItem orderItem_5_1 = new OrderItem(storeItem_5, 1);

    protected List<DiscountsItem> discounts_1 = List.of(discountsItem_1234);
    protected List<OrderItem> items_1 = Arrays.asList(orderItem_3_1, orderItem_2_5, orderItem_5_1);

    protected Order order_withDiscount_1234 = new Order(
            1,
            discounts_1,
            items_1
    );

    // Result

    protected ResultItem resultItem_3_1 = new ResultItem(
            orderItem_3_1.quantity(),
            storeItem_3.name(),
            storeItem_3.price(), // 1.25
            BigDecimal.valueOf(1.25)
    );
    protected ResultItem resultItem_2_5 = new ResultItem(
            orderItem_2_5.quantity(),
            storeItem_2.name() + DISCOUNT_DESCR,
            storeItem_2.price(), // 0.50
            BigDecimal.valueOf(2.25) // 2.5 -> 2.25
    );
    protected ResultItem resultItem_5_1 = new ResultItem(
            orderItem_5_1.quantity(),
            storeItem_5.name(),
            storeItem_5.price(), // 0.34
            BigDecimal.valueOf(0.34)
    );

    protected List<ResultItem> resultItems_1 = Arrays.asList(resultItem_3_1, resultItem_2_5, resultItem_5_1);
}
