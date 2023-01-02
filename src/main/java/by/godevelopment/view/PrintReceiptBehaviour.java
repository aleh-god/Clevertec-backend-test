package by.godevelopment.view;

import by.godevelopment.domain.models.Receipt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public interface PrintReceiptBehaviour {

    public void printReceipt(Receipt receipt);

    class BaseImpl implements PrintReceiptBehaviour {
        @Override
        public void printReceipt(Receipt receipt) {
            if (receipt !=null) {
                receipt.lines().forEach(System.out::println);
            }
            else throw new IllegalStateException();
        }
    }

    class WriteFileImpl implements PrintReceiptBehaviour {

        String name = "ReceiptOut.txt";

        public WriteFileImpl() {
        }

        public WriteFileImpl(String file) {
            if (file != null) name = file;
        }

        @Override
        public void printReceipt (Receipt receipt){

            if (receipt !=null) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(name))) {
                    for (String s : receipt.lines()) {
                        bw.write(s);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            else throw new IllegalStateException();
        }
    }
}
