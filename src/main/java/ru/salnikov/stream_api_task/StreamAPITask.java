package ru.salnikov.stream_api_task;

import ru.salnikov.entities.Person;
import ru.salnikov.fileworker.FileManager;
import ru.salnikov.repositories.ObjRepository;
import ru.vsu.lab.entities.IDivision;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamAPITask {
    private ObjRepository<Person> repository;

    public StreamAPITask() {
        repository = FileManager.LoadPersonsFromFile("C:\\Users\\Denvormine\\IdeaProjects\\task_1\\src\\main\\resources\\persons.csv");
    }

    public Map<IDivision, BigDecimal> DivisionBySalary() {
        return repository.toList().stream().collect(
                Collectors.groupingBy(Person::getDivision,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Person::getSalary,
                                BigDecimal::add)));
    }

    public LinkedList<Person> PersonOlderThan30_NameHasA_SalaryLowerThan30000() {
        return repository.toList().stream().filter(person ->
                person.getFirstName().toLowerCase().contains("a") &&
                person.getAge() > 30 &&
                person.getSalary().multiply(new BigDecimal("12")).compareTo(new BigDecimal("30000")) < 0)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    public boolean PersonWithTwoAInRowInName() {
        return repository.toList().
                stream().
                anyMatch(person ->
                        person.getFirstName().toLowerCase().contains("aa"));
    }

    public Map<Integer, Long> YearAndAmountOfPeopleBornThatYear() {
        return repository.toList().stream().collect(
                        Collectors.groupingBy(
                        x->x.getBirthdate().getYear(), Collectors.counting()));
    }

}
