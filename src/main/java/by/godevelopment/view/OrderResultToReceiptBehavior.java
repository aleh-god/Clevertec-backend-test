package by.godevelopment.view;

import by.godevelopment.domain.models.OrderResult;
import by.godevelopment.domain.models.Receipt;
import by.godevelopment.domain.models.ResultItem;

import java.util.ArrayList;
import java.util.List;

public interface OrderResultToReceiptBehavior {

    // Behavior for local context
    String SPACE = "  ";
    String PERCENT_CHAR = "%";
    String HEADER = "Receipt #";
    String TOTAL = "Total: ";
    String DISCOUNT = "Discount: ";
    String TOTAL_WITH_DISC = "Total with discount: ";
    String HEADER_LINE = "QTY  Description  Price  Total";
    String FOOTER_LINE = "##############################";
    String ERROR_MESSAGE = "Error: Receipt is unnavigable!";

    public Receipt invoke(OrderResult orderResult);

    class BaseImpl implements OrderResultToReceiptBehavior {
        @Override
        public Receipt invoke(OrderResult orderResult) {

            if (orderResult != null) {
                List<String> lines = new ArrayList<>();

                lines.add(
                        new StringBuilder(HEADER)
                        .append(orderResult.header())
                        .toString()
                );
                lines.add(HEADER_LINE);
                orderResult.resultItems().stream().map(this::convertToString).forEach(lines::add);
                lines.add(FOOTER_LINE);
                lines.add(
                        new StringBuilder(TOTAL)
                        .append(orderResult.total())
                        .toString()
                );
                if (orderResult.discountRate().rate() > 0) lines.add(
                        new StringBuilder(DISCOUNT)
                        .append(orderResult.discountRate().name())
                        .append(SPACE)
                        .append(orderResult.discountRate().rate())
                        .append(PERCENT_CHAR)
                        .toString()
                );
                lines.add(
                        new StringBuilder(TOTAL_WITH_DISC)
                        .append(orderResult.totalWithDiscount())
                        .toString()
                );
                return new Receipt(
                        orderResult.header(),
                        lines
                );
            }
            else return new Receipt(
                    -1,
                    List.of(ERROR_MESSAGE)
            );
        }

        private String convertToString(ResultItem resultItem) {
            return new StringBuilder()
                    .append(resultItem.quantity())
                    .append(SPACE)
                    .append(resultItem.description())
                    .append(SPACE)
                    .append(resultItem.price())
                    .append(SPACE)
                    .append(resultItem.total())
                    .toString();
        }
    }
}
