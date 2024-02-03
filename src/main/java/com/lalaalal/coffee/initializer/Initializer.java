package com.lalaalal.coffee.initializer;

import com.lalaalal.coffee.initializer.Initialize.Time;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class Initializer {
    protected static void initialize(Class<?> initializer, Time time) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .addScanners(Scanners.MethodsAnnotated.filterResultsBy(s -> true))
                        .forPackage("com.lalaalal.coffee")
        );
        Set<Method> classes = reflections.getMethodsAnnotatedWith(Initialize.class);
        try {
            for (Method method : classes) {
                if (method.getParameterCount() > 0 || method.getReturnType() != void.class)
                    continue;
                Initialize[] annotations = method.getAnnotationsByType(Initialize.class);
                if (annotations.length == 1
                        && annotations[0].with().equals(initializer)
                        && annotations[0].time().equals(time))
                    method.invoke(null);
            }
        } catch (InvocationTargetException exception) {
            // TODO : handle exception
            exception.printStackTrace();
        } catch (IllegalAccessException exception) {
            exception.getMessage();
        }

    }
}
