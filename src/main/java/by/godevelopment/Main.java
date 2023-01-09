package by.godevelopment;

import by.godevelopment.di.ServiceLocator;
import by.godevelopment.di.ServiceLocatorImpl;
import by.godevelopment.domain.models.Order;
import by.godevelopment.domain.models.OrderResult;
import by.godevelopment.domain.models.Receipt;

public class Main {
    public static void main(String[] args) {

        String fileNameIn = null;
        String fileNameOut = null;
        ServiceLocator serviceLocator;

        try {
            System.out.println("Demo is starting.");
            fileNameIn = args[0];
            fileNameOut = args[1];
            System.out.println("Have taken args:" + fileNameIn + " " + fileNameOut);
        } catch (Exception e) {
            System.out.println("Error: Init arguments is empty!");
        } finally {
            serviceLocator = new ServiceLocatorImpl(fileNameIn, fileNameOut);
            System.out.println("DataSources have created.");
            System.out.println("Utils have created.");
        }

        try {
            String inputData = serviceLocator.provideStringDataSource().getInputDataOrNull();
            Order order = serviceLocator.provideCreateOrderUseCase().createOrderOrReturnNull(inputData);
            OrderResult orderResult = serviceLocator.provideCalculateOrderToOrderResultUseCase().calculateOrNull(order);
            Receipt receipt = serviceLocator.provideOrderResultToReceiptBehavior().invoke(orderResult);
            serviceLocator.providePrintReceiptBehaviour().invoke(receipt);
        } catch (Exception e) {
            System.out.println("Demo has crashed! Error: " + e.getMessage());
        } finally {
            System.out.println("Demo has ended!");
        }
    }
}
