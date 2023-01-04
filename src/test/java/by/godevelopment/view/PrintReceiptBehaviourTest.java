package by.godevelopment.view;

import by.godevelopment.domain.models.Receipt;
import by.godevelopment.testsources.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.godevelopment.view.OrderResultToReceiptBehavior.ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrintReceiptBehaviourTest extends BaseTest {

    String name = "test.txt";

    PrintReceiptBehaviour printReceiptBehaviour = new PrintReceiptBehaviour.WriteFileImpl(name);

    @AfterEach
    void clearResources() {
        File testRes = new File(name);
        boolean deleted = testRes.delete();
        if(deleted) System.out.println("Files has been deleted");
        else System.out.println("Files has NOT been deleted");
    }

    @Test
    void printReceipt_passNull_returnException() {
        assertThrows(IllegalStateException.class, () -> {
            printReceiptBehaviour.invoke(null);
        });
    }

    @Test
    void printReceipt_passErrorMessage_isCorrect() {

        printReceiptBehaviour.invoke(new Receipt(
                -1,
                List.of(ERROR_MESSAGE)
        ));

        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String actual = br.readLine();
            assertEquals(ERROR_MESSAGE, actual);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void printReceipt_isCorrect() {

        List<String> expected = List.of("Receipt #1",
                "QTY  Description Price  Total",
                "1  Muesli  1.25  1.25",
                "5  Fresh milk  with -10%  0.5  2.25",
                "1  Potatoes  0.34  0.34",
                "##############################",
                "Total: 3.84",
                "Total with discount: 3.84");

        Receipt receipt = new Receipt(
                1,
                expected
        );

        printReceiptBehaviour.invoke(receipt);
        List<String> actual = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String s;
            while((s=br.readLine())!=null) {
                actual.add(s);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        assertEquals(expected, actual);
    }
}
