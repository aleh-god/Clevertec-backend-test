package by.godevelopment.domain.usecases;

import by.godevelopment.data.DiscountItemsDataSource;
import by.godevelopment.data.StoreItemsDataSource;
import by.godevelopment.domain.models.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static by.godevelopment.domain.usecases.CalculateOrderItemToResultItemUseCase.DISCOUNT_DESCR;
import static by.godevelopment.domain.usecases.CalculateOrderToOrderResultUseCase.nullObjectDiscountsItem;

public class BaseUseCaseTest {

    DiscountItemsDataSource discountItemsDataSource = new DiscountItemsDataSource.BaseImpl();

    StoreItemsDataSource storeItemsDataSource = new StoreItemsDataSource.BaseImpl();
    List<Integer> ids = storeItemsDataSource.getStoreItemsId();
    ParseStringToOrderItemUseCase parseStringToOrderItemUseCase = new ParseStringToOrderItemUseCase.BaseImpl(
            ParseStringToOrderItemUseCase.SEPARATOR,
            new ValidateOrderItemIdInStoreUseCase.BaseImpl(ids),
            new StoreItemsDataSource.BaseImpl()
    );

    // 3-1 2-5 5-1 card-1234
    DiscountsItem discountsItem_1234 = discountItemsDataSource.getDiscountsItemByStringOrNull("card-1234");
    StoreItem storeItem_3 = storeItemsDataSource.getStoreItemByIdOrNull(3);
    StoreItem storeItem_2 = storeItemsDataSource.getStoreItemByIdOrNull(2);
    StoreItem storeItem_5 = storeItemsDataSource.getStoreItemByIdOrNull(5);
    OrderItem orderItem_3_1 = new OrderItem(storeItem_3, 1);
    OrderItem orderItem_2_5 = new OrderItem(storeItem_2, 5);
    OrderItem orderItem_5_1 = new OrderItem(storeItem_5, 1);

    List<DiscountsItem> discounts_1 = Arrays.asList(discountsItem_1234);
    List<OrderItem> items_1 = Arrays.asList(orderItem_3_1, orderItem_2_5, orderItem_5_1);

    Order order_withDiscount_1234 = new Order(
            1,
            discounts_1,
            items_1
    );

    // Result

    ResultItem resultItem_3_1 = new ResultItem(
            orderItem_3_1.quantity(),
            storeItem_3.name(),
            storeItem_3.price(), // 1.25
            BigDecimal.valueOf(1.25)
    );
    ResultItem resultItem_2_5 = new ResultItem(
            orderItem_2_5.quantity(),
            storeItem_2.name() + DISCOUNT_DESCR,
            storeItem_2.price(), // 0.50
            BigDecimal.valueOf(2.25) // 2.5 -> 2.25
    );
    ResultItem resultItem_5_1 = new ResultItem(
            orderItem_5_1.quantity(),
            storeItem_5.name(),
            storeItem_5.price(), // 0.34
            BigDecimal.valueOf(0.34)
    );

    List<ResultItem> resultItems_1 = Arrays.asList(resultItem_3_1, resultItem_2_5, resultItem_5_1);

    OrderResult orderResult_withDiscount = new OrderResult(
            1,
            resultItems_1,
            BigDecimal.valueOf(3.84), // 1.25 + 2.25 + 0.34 -> 3.84
            discountsItem_1234, // 15%
            BigDecimal.valueOf(3.26)
    );

    OrderResult orderResult_withoutDiscount = new OrderResult(
            1,
            resultItems_1,
            BigDecimal.valueOf(3.84), // 1.25 + 2.25 + 0.34 -> 3.84
            nullObjectDiscountsItem,
            BigDecimal.valueOf(3.84)
    );
}
