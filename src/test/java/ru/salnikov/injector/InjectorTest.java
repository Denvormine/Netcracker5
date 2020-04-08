package ru.salnikov.injector;

import org.junit.Assert;
import org.junit.Test;
import ru.salnikov.annotations.ForInjection;
import ru.salnikov.entities.Person;
import ru.salnikov.exceptions.InjectorException;
import ru.salnikov.repositories.ObjRepository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class InjectorTest {

    @Test
    public void inject() throws InjectorException {
        ObjRepository<Person> testRepository = new ObjRepository<>();
        for (int i = 9; i >= 0; i--) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            testRepository.add(person);
        }
        ObjRepository<Person> sortedRepository = new ObjRepository<>();
        for (int i = 0; i < 10; i++) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            sortedRepository.add(person);
        }

        Injector.inject(testRepository);
        testRepository.sortBy(testRepository.comparator);

        Assert.assertEquals(testRepository, sortedRepository);
    }

    @Test
    public void inject2() {
        ObjRepository<Person> objRepository = new ObjRepository<>();
        Field[] fields = objRepository.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getAnnotatedType());
        }
    }
}