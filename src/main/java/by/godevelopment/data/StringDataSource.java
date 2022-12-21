package by.godevelopment.data;

public interface StringDataSource {

    public String getInputDataOrNull();

    class BaseImpl implements StringDataSource {

        private String inputData;

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

    class ReadInputFileImpl implements StringDataSource {
        @Override
        public String getInputDataOrNull() {
            return null;
        }
    }
}
