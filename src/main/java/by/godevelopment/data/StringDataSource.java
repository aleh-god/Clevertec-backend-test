package by.godevelopment.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface StringDataSource {

    public String getInputDataOrNull();

    class BaseImpl implements StringDataSource {

        private final String inputData;

        public BaseImpl() {
            inputData = "3-1 2-5 5-1 card-1234";
        }

        public BaseImpl(String inputData) {

            this.inputData = inputData;
        }

        @Override
        public String getInputDataOrNull() {
            return inputData;
        }
    }

    class ReadFileImpl implements StringDataSource {

        String name = "OrderIn.txt";

        public ReadFileImpl() {
        }

        public ReadFileImpl(String file) {
            if (file != null) name = file;
        }

        @Override
        public String getInputDataOrNull() {
            try (BufferedReader br = new BufferedReader(new FileReader(name))) {
                return br.readLine();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return null;
            }
        }
    }
}
