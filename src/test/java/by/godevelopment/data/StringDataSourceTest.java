package by.godevelopment.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringDataSourceTest {

    String name = "test.txt";

    @BeforeEach
    void createResources() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(name))) {
            bw.write("3-1 2-5 5-1 card-1234");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    void clearResources() {
        File testRes = new File(name);
        boolean deleted = testRes.delete();
        if(deleted) System.out.println("Files has been deleted");
        else System.out.println("Files has NOT been deleted");
    }

    @Test
    void getInputDataOrNull_isCorrect() {

        StringDataSource stringDataSource = new StringDataSource.ReadFileImpl(name);

        String actual = stringDataSource.getInputDataOrNull();

        assertEquals("3-1 2-5 5-1 card-1234", actual);
    }

    @Test
    void getInputDataOrNull_passWrongFileName_returnNull() {

        StringDataSource stringDataSource = new StringDataSource.ReadFileImpl("WrongName.txt");

        String actual = stringDataSource.getInputDataOrNull();

        assertNull(actual);
    }
}
