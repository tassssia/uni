package task4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassDescriber {

    public static void describeClass(String className) {
        try {
            MyClassLoader loader = new MyClassLoader();
            Class<?> classToDesc = loader.findClass(className);

            System.out.println("Class Name: " + classToDesc.getName());

            int classModifiers = classToDesc.getModifiers();
            System.out.println("Modifiers: " + Modifier.toString(classModifiers));

            Class<?> superClass = classToDesc.getSuperclass();
            if (superClass != null) {
                System.out.println("Superclass: " + superClass.getName());
            }

            Class<?>[] interfaces = classToDesc.getInterfaces();
            if (interfaces.length > 0) {
                System.out.println("\nImplemented Interfaces:");
                for (Class<?> iface : interfaces) {
                    System.out.println("  " + iface.getName());
                }
            }

            System.out.println("\nFields:");
            for (Field field : classToDesc.getDeclaredFields()) {
                int fieldModifiers = field.getModifiers();
                System.out.println("  " + Modifier.toString(fieldModifiers) + " " +
                        field.getName() + ": " + field.getType().getName());
            }

            System.out.println("\nMethods:");
            for (Method method : classToDesc.getDeclaredMethods()) {
                int methodModifiers = method.getModifiers();
                System.out.println("  " + Modifier.toString(methodModifiers) + " " +
                        method.getName() + ": " + method.getReturnType().getName());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + className);
        }
    }
}
