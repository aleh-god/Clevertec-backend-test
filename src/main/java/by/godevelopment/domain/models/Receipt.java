package by.godevelopment.domain.models;

import java.util.List;

public record Receipt(
    String header,
    List<String> receiptItems,
    String total,
    String rateDiscount,
    String totalWithDiscount
) { }

