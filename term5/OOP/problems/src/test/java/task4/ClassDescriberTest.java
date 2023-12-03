package task4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassDescriberTest {

    @Test
    public void testDescribeClass() {
        String className = "task4.SampleClass";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            ClassDescriber.describeClass(className);

            String[] expectedInfo = {
                    "task4.SampleClass",
                    "Modifiers: public",
                    "Superclass: task4.SuperClass",
                    "task4.SampleInterface",
                    "private field1: int",
                    "private field2: java.lang.String",
                    "public getField1: int",
                    "public setField2: void",
                    "public setField1: void",
                    "public getField2: java.lang.String"
            };

            for(String pieceOfInfo: expectedInfo) {
                assertTrue(outputStream.toString().contains(pieceOfInfo));
            }
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    public void testDescribeNotExistingClass() {
        String className = "task4.NotExistingClass";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            ClassDescriber.describeClass(className);

            String expectedOutput = "Class not found: task4.NotExistingClass";
            assertTrue(outputStream.toString().contains(expectedOutput));
        } finally {
            System.setOut(System.out);
        }
    }
}
