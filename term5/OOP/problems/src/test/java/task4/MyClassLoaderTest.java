package task4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyClassLoaderTest {

    private MyClassLoader loader = new MyClassLoader();

    @Test
    public void testLoadClass() throws ClassNotFoundException {
        String className = "task4.SampleClass";

        try {
            Class<?> loadedClass = loader.findClass(className);

            assertNotNull(loadedClass);
            assertEquals(className, loadedClass.getName());
        } catch (ClassNotFoundException e) {
            fail("ClassNotFoundException should not be thrown");
        }
    }

    @Test
    public void testLoadNonExistentClass() {
        String className = "NotExistingClass";

        try {
            loader.findClass(className);
            fail("ClassNotFoundException should be thrown");
        } catch (ClassNotFoundException e) {
            // Expected exception
        }
    }
}
