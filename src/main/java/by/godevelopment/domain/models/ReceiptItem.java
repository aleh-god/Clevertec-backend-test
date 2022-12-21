package by.godevelopment.domain.models;

import java.math.BigDecimal;

public class ReceiptItem extends ReceiptItemFrame {

    private final int quantity;

    private final String description;

    private final BigDecimal price;

    public ReceiptItem(int quantity, String description, BigDecimal price) {
        this.quantity = quantity;
        this.description = description;
        this.price = price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public BigDecimal getTotal() {
        BigDecimal bigQty = BigDecimal.valueOf(quantity);
        return price.multiply(bigQty);
    }
}
