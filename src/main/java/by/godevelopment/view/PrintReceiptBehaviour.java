package by.godevelopment.view;

import by.godevelopment.domain.models.Receipt;

import java.io.BufferedWriter;
import java.io.FileWriter;

public interface PrintReceiptBehaviour {

    public void invoke(Receipt receipt) throws IllegalStateException;

    class BaseImpl implements PrintReceiptBehaviour {
        @Override
        public void invoke(Receipt receipt) throws IllegalStateException {
            if (receipt !=null) {
                receipt.lines().forEach(System.out::println);
            }
            else throw new IllegalStateException();
        }
    }

    class WriteFileImpl implements PrintReceiptBehaviour {

        String name;

        public WriteFileImpl() {
            name = "ReceiptOut.txt";
        }

        public WriteFileImpl(String file) {
            if (file != null) name = file;
        }

        @Override
        public void invoke(Receipt receipt) throws IllegalStateException {

            if (receipt !=null) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(name))) {
                    for (String s : receipt.lines()) {
                        bw.write(s);
                        bw.newLine();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException();
                }
            }
            else throw new IllegalStateException();
        }
    }
}
