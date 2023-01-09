package by.godevelopment.domain.models;

import java.math.BigDecimal;
import java.util.List;

public record OrderResult (
        int header,
        List<ResultItem> resultItems,
        BigDecimal total,
        DiscountsItem discountRate,
        BigDecimal totalWithDiscount
) { }
