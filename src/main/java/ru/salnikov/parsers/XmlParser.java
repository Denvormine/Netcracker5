package ru.salnikov.parsers;

import ru.salnikov.entities.Division;
import ru.salnikov.entities.Person;
import ru.salnikov.repositories.PersonDynamicArray;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParser {

    public static void parseArrayToXml(PersonDynamicArray array) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PersonDynamicArray.class, Person.class, Division.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(array, new File("./array.xml"));
    }

    public static PersonDynamicArray parseXmlToArray() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PersonDynamicArray.class, Person.class, Division.class);
        Unmarshaller un = context.createUnmarshaller();
        return (PersonDynamicArray) un.unmarshal(new File("./array.xml"));
    }


}
