import org.junit.Test;
import ru.salnikov.entities.Person;
import ru.salnikov.injector.Injector;
import ru.salnikov.repositories.PersonDynamicArray;
import ru.vsu.lab.entities.IPerson;
import ru.vsu.lab.repository.IRepository;

import java.io.Console;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonDynamicArrayTest {

    @Test
    public void delete() {
        PersonDynamicArray array = new PersonDynamicArray();
        for (int i = 0; i < 10; i++) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            array.add(person);
        }
        PersonDynamicArray deletedArray = new PersonDynamicArray();
        for (int i = 0; i < 10; i++) {
            if (i == 6) {
                continue;
            }
            Person person = Person.createEmptyPerson();
            person.setId(i);
            deletedArray.add(person);
        }

        array.delete(6);

        assertEquals(deletedArray, array);

    }

    @Test
    public void get() {
        PersonDynamicArray array = new PersonDynamicArray();
        for (int i = 0; i < 10; i++) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            array.add(person);
        }

        Person testPerson = Person.createEmptyPerson();
        testPerson.setId(5);
        IPerson arrayPerson = array.get(5);

        assertEquals(testPerson, arrayPerson);
    }

    @Test
    public void toList() {
        PersonDynamicArray array = new PersonDynamicArray();
        List<IPerson> personList = new LinkedList<IPerson>();
        for (int i = 0; i < 10; i++) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            array.add(person);
            personList.add(person);
        }

        List<IPerson> arrayToList = array.toList();

        assertEquals(personList, arrayToList);
    }

    @Test
    public void sortBy() {
        PersonDynamicArray array = new PersonDynamicArray();
        for (int i = 9; i >= 0; i--) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            array.add(person);
        }
        PersonDynamicArray expectedArray = new PersonDynamicArray();
        for (int i = 0; i < 10; i++) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            expectedArray.add(person);
        }
        Comparator<IPerson> comparator = new Comparator<IPerson>() {
            @Override
            public int compare(IPerson o1, IPerson o2) {
                return o1.getId() - o2.getId();
            }
        };

        try {
            Injector.inject(array);
        }
        catch(Exception ex) {
            System.err.println(ex);
            System.exit(1);
        }

        array.sortBy(comparator);

        for (int i = 0; i < 10; i++) {
            System.out.println(array.get(i).getId());
        }

        assertEquals(expectedArray, array);
    }

    @Test
    public void searchBy() {
        PersonDynamicArray array = new PersonDynamicArray();
        for (int i = 0; i < 10; i++) {
            Person person = Person.createEmptyPerson();
            person.setId(i);
            array.add(person);
        }
        PersonDynamicArray expectedArray = new PersonDynamicArray();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Person person = Person.createEmptyPerson();
                person.setId(i);
                expectedArray.add(person);
            }
        }

        IRepository actualArray = array.searchBy(x -> x.getId() % 2 == 0);

        assertEquals(expectedArray, actualArray);
    }

}