package ru.salnikov.stream_api_task;

import org.junit.Test;
import ru.salnikov.entities.Person;
import ru.salnikov.repositories.ObjRepository;
import ru.vsu.lab.entities.IDivision;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.*;

public class StreamAPITaskTest {

    @Test
    public void divisionBySalary() {
        StreamAPITask task = new StreamAPITask();

        Map<IDivision, BigDecimal> map = task.DivisionBySalary();

        System.out.println(map);
    }

    @Test
    public void personOlderThan30_NameHasA_SalaryLowerThan30000() {
        StreamAPITask task = new StreamAPITask();

        LinkedList<Person> repository = task.PersonOlderThan30_NameHasA_SalaryLowerThan30000();

        System.out.println(repository);
    }

    @Test
    public void personWithTwoAInRowInName() {
        StreamAPITask task = new StreamAPITask();

        boolean OK = task.PersonWithTwoAInRowInName();

        System.out.println(OK);
    }

    @Test
    public void yearAndAmountOfPeopleBornThatYear() {
        StreamAPITask task = new StreamAPITask();

        Map<Integer, Long> map = task.YearAndAmountOfPeopleBornThatYear();

    }
}