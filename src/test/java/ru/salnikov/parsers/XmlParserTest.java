package ru.salnikov.parsers;

import org.junit.Assert;
import org.junit.Test;
import ru.salnikov.entities.Division;
import ru.salnikov.entities.Person;
import ru.salnikov.repositories.PersonDynamicArray;
import ru.vsu.lab.entities.enums.Gender;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class XmlParserTest {

    @Test
    public void parseArray() throws JAXBException {
        PersonDynamicArray array = new PersonDynamicArray();
        for (int i = 0; i < 10; i++) {
            Person person = new Person(i, "firstName " + i, "lastName " + i, LocalDate.now(), Gender.MALE, new Division(i, "Division " + i));
            array.add(person);
        }
        XmlParser.parseArrayToXml(array);
        PersonDynamicArray expectedArray = XmlParser.parseXmlToArray();
        Assert.assertEquals(array, expectedArray);
    }
}