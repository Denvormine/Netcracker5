package ru.salnikov.injector;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.salnikov.annotations.ForInjection;
import ru.salnikov.exceptions.InjectorException;
import ru.salnikov.fileworker.FileManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class Injector {

    private static final Logger logger = LoggerFactory.getLogger(Injector.class);

    public static void inject(Object obj) throws InjectorException {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Map<Class, Class> classMap = FileManager.LoadInjectionFromFile("C:\\Users\\Denvormine\\IdeaProjects\\task_1\\src\\main\\resources\\injector.cfg");
            logger.info("Implementations for injections was loaded");
            for (Field field : fields) {
                if (field.getAnnotation(ForInjection.class) != null) {
                    field.setAccessible(true);
                    Class impl = classMap.get(field.getType());
                    if (impl == null) {
                        throw new Exception("No implementation for " + field.getAnnotatedType().toString());
                    }
                    logger.trace(impl.getName() + " was set for object " + obj.getClass().getName());
                    field.set(obj, impl.getConstructor().newInstance());
                }
            }
        } catch (Exception exception) {
            logger.error("Exception was caught in injector: " + exception.getMessage());
            throw new InjectorException(exception);
        }

    }
}
