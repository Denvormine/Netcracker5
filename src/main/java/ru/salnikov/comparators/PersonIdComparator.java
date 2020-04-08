package ru.salnikov.comparators;

import ru.salnikov.entities.Person;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Comparator;

@XmlType(name = "cat")
@XmlRootElement
public class PersonIdComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o1.getId() - o2.getId();
    }

}
