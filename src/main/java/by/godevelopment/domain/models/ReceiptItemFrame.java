package by.godevelopment.domain.models;

import java.math.BigDecimal;

public abstract class ReceiptItemFrame {

    public abstract int getQuantity();

    public abstract String getDescription();

    public abstract BigDecimal getPrice();
    public abstract BigDecimal getTotal();
}
