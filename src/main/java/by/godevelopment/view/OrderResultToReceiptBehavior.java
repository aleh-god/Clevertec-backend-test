package by.godevelopment.view;

import by.godevelopment.domain.models.OrderResult;
import by.godevelopment.domain.models.Receipt;
import by.godevelopment.domain.models.ResultItem;

import java.util.List;

public interface OrderResultToReceiptBehavior {

    // Behavior for local context
    String SPACE = " ";
    String PERCENT_CHAR = "%";
    String HEADER = "Receipt #";

    String TOTAL = "Total: ";
    String DISCOUNT = "Discount: ";
    String TOTAL_WITH_DISC = "Total with discount: ";

    public Receipt invoke(OrderResult orderResult);

    class BaseImpl implements OrderResultToReceiptBehavior {
        @Override
        public Receipt invoke(OrderResult orderResult) {

            String header = HEADER + orderResult.header();
            List<String> receiptItems = orderResult.resultItems().stream().map(this::convertToString).toList();
            String total = TOTAL + orderResult.total().toString();
            String rateDiscount = DISCOUNT + orderResult.rateDiscount().name() + SPACE + orderResult.rateDiscount().rate()
                    + PERCENT_CHAR;
            String totalWithDiscount = TOTAL_WITH_DISC + orderResult.totalWithDiscount().toString();

            return new Receipt(
                    header,
                    receiptItems,
                    total,
                    rateDiscount,
                    totalWithDiscount
            );
        }

        private String convertToString(ResultItem resultItem) {
            return resultItem.quantity() + SPACE + resultItem.description() + SPACE + resultItem.price() + SPACE
                    + resultItem.total();
        }
    }
}
