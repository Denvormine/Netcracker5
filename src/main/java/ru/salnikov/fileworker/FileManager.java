package ru.salnikov.fileworker;

import ru.salnikov.entities.Division;
import ru.salnikov.entities.Person;
import ru.salnikov.repositories.ObjRepository;
import ru.salnikov.repositories.PersonDynamicArray;
import ru.vsu.lab.entities.IDivision;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Class to work fith files, that contains persons
 */
public class FileManager {
    /**
     * @param filename Path to the text file with persons
     * @return DynamicArray of persons from text file
     */
    public static ObjRepository<Person> LoadPersonsFromFile(String filename) {
        List<String> lines;
        try {
            Scanner scanner = new Scanner(new File(filename));
            lines = new ArrayList<String>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }

        }
        catch (FileNotFoundException ex) {
            System.err.println("Файл не найден!");
            return null;
        }
        String[] stringsArray = lines.toArray(new String[0]);
        ObjRepository<Person> repository = new ObjRepository<>();
        HashMap<String, IDivision> divisionHashMap = new HashMap<String, IDivision>();
        for (int i = 1; i < lines.size(); i++) {
            String[] stringPerson = stringsArray[i].split(";");
            Person person = Person.createEmptyPerson();
            person.setId(Integer.parseInt(stringPerson[0]));
            person.setLastName("");
            person.setFirstName(stringPerson[1]);
            person.setGender(stringPerson[2]);
            String[] stringDate = stringPerson[3].split("\\.");
            LocalDate birthdate = LocalDate.of(
                    Integer.parseInt(stringDate[2]),
                    Integer.parseInt(stringDate[1]),
                    Integer.parseInt(stringDate[0]));
            person.setBirthdate(birthdate);
            if (divisionHashMap.containsKey(stringPerson[4])) {
                person.setDivision(divisionHashMap.get(stringPerson[4]));
            }
            else {
                IDivision division = new Division(i, stringPerson[4]);
                divisionHashMap.put(stringPerson[4], division);
                person.setDivision(division);
            }
            person.setSalary(new BigDecimal(stringPerson[5]));
            repository.add(person);
        }
        return repository;
    }

    public static Map<Class, Class> LoadInjectionFromFile(String filename) throws FileNotFoundException, IllegalArgumentException, ClassNotFoundException {
        List<String> lines;
        Scanner scanner = new Scanner(new File(filename));
        lines = new ArrayList<String>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        String[] stringsArray = lines.toArray(new String[0]);
        Map<Class, Class> map = new HashMap<Class, Class>();
        for (String str : stringsArray) {
            String[] stringMap = str.split("=");
            if (stringMap.length != 2) {
                throw new IllegalArgumentException("File information has incorrect format");
            }
            map.put(Class.forName(stringMap[0].trim()), Class.forName(stringMap[1].trim()));
        }
        return map;
    }
}
