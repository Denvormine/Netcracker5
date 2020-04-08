import org.junit.Test;
import ru.salnikov.entities.Person;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void getAge() {
        Person person = Person.createEmptyPerson();
        person.setBirthdate(LocalDate.now().minusYears(20));

        int age = person.getAge();
        int expectedAge = 20;

        assertEquals(20, age);
    }
}