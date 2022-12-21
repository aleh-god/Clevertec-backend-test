package by.godevelopment.domain.models;

import java.math.BigDecimal;

public class ItemsMoreThanLimitDiscountDecorator extends ReceiptItemDecorator {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final float ZERO = 0F;
    public static final String DESCR_DIVIDER = " with ";
    private final ReceiptItemFrame receiptItemFrame;
    private final DiscountsItem discountsItem;
    public final int LOW_LIMIT;

    public ItemsMoreThanLimitDiscountDecorator(ReceiptItemFrame receiptItemFrame, DiscountsItem discountsItem) {
        this.receiptItemFrame = receiptItemFrame;
        this.discountsItem = discountsItem;
        LOW_LIMIT = 5;
    }

    public ItemsMoreThanLimitDiscountDecorator(ReceiptItemFrame receiptItemFrame, DiscountsItem discountsItem, int customLowLimit) {
        this.receiptItemFrame = receiptItemFrame;
        this.discountsItem = discountsItem;
        LOW_LIMIT = customLowLimit;
    }

    @Override
    public int getQuantity() {
        return receiptItemFrame.getQuantity();
    }

    @Override
    public String getDescription() {
        return receiptItemFrame.getDescription() + DESCR_DIVIDER + discountsItem.name();
    }

    @Override
    public BigDecimal getPrice() {
        return null;
    }

    @Override
    public BigDecimal getTotal() {
        if (receiptItemFrame.getQuantity() > LOW_LIMIT && discountsItem.rate() > ZERO) {
            BigDecimal pct = BigDecimal.valueOf(discountsItem.rate());
            BigDecimal percentageResult = percentage(receiptItemFrame.getTotal(), pct);
            return receiptItemFrame.getTotal().subtract(percentageResult);
        }
        else return receiptItemFrame.getTotal();
    }

    private static BigDecimal percentage(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(ONE_HUNDRED);
    }
}
