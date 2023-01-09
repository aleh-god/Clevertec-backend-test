package by.godevelopment.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static by.godevelopment.constants.AppConfig.DEFAULT_ORDER;
import static by.godevelopment.constants.AppConfig.DEFAULT_READ_FILE_NAME;

public interface StringDataSource {

    public String getInputDataOrNull();

    class BaseImpl implements StringDataSource {

        private final String inputData;

        public BaseImpl() {
            inputData = DEFAULT_ORDER;
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

        String name;

        public ReadFileImpl() {
            name = DEFAULT_READ_FILE_NAME;
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
