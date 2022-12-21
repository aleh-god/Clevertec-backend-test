package by.godevelopment.domain.models;

import java.util.List;

public record Order(
        int id,
        List<DiscountsItem> discounts,
        List<OrderItem> items
) { }
