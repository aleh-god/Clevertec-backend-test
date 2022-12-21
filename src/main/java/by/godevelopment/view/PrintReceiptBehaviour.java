package by.godevelopment.view;

import by.godevelopment.domain.models.Receipt;

public interface PrintReceiptBehaviour {

    public void printReceipt(Receipt receipt);

    class BaseImpl implements PrintReceiptBehaviour {
        @Override
        public void printReceipt(Receipt receipt) {
            System.out.println(receipt.header());
            for (String item : receipt.receiptItems()) {
                System.out.println(item);
            }
            System.out.println(receipt.total());
            System.out.println(receipt.rateDiscount());
            System.out.println(receipt.totalWithDiscount());
        }
    }
}
