package com.lalaalal.coffee.initializer;

import com.lalaalal.coffee.exception.FatalError;
import com.lalaalal.coffee.initializer.Initialize.Time;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Set;

public interface Initializer {
    static void initialize(Class<? extends Initializer> initializer, Time time) throws FatalError {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .addScanners(Scanners.MethodsAnnotated.filterResultsBy(s -> true))
                        .forPackage("com.lalaalal.coffee")
        );
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Initialize.class);
        try {
            for (Method method : methods) {
                if (method.getParameterCount() > 0 || method.getReturnType() != void.class)
                    continue;
                Initialize[] annotations = method.getAnnotationsByType(Initialize.class);
                if (annotations.length == 1
                        && annotations[0].with().equals(initializer)
                        && annotations[0].time().equals(time))
                    method.invoke(null);
            }
        } catch (Throwable exception) {
            throw new FatalError(exception.getCause());
        }
    }
}
