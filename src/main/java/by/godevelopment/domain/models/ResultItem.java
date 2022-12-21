package by.godevelopment.domain.models;

import java.math.BigDecimal;

public record ResultItem(
        int quantity,
        String description,
        BigDecimal price,
        BigDecimal total
) {
}
