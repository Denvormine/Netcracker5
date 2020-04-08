package ru.salnikov.entities;

import ru.salnikov.adapters.LocalDateAdapter;
import ru.salnikov.factories.LabFactory;
import ru.vsu.lab.entities.IDivision;
import ru.vsu.lab.entities.IPerson;
import ru.vsu.lab.entities.enums.Gender;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * The class for the person, that has unique id, surname, name, patronymic and birthday.
 * @author Denvormine
 *
 */
@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements IPerson {
    /**
     * The unique number for the ru.salnikov.entities.Person, that can be any number, bigger than 0.
     */
    @XmlElement
    private Integer id;
    /**
     * The first name of the ru.salnikov.entities.Person.
     */
    @XmlElement
    private String firstName;
    /**
     * The last name of the ru.salnikov.entities.Person.
     */
    @XmlElement
    private String lastName;

    /**
     * The date, representing ru.salnikov.entities.Person's birthday.
     */
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate birthdate;
    /**
     * The gender of the ru.salnikov.entities.Person
     */
    @XmlElement
    private Gender gender;

    /**
     * The division of the ru.salnikov.entities.Person working at
     */
    @XmlAnyElement(lax = true)
    private IDivision division;

    @XmlElement
    private BigDecimal salary;

    /**
     * @param id The unique number for the ru.salnikov.entities.Person, that can be any number, bigger than 0.
     * @param firstName The first name of the ru.salnikov.entities.Person.
     * @param lastName The last name of the ru.salnikov.entities.Person.
     * @param birthdate The date, representing ru.salnikov.entities.Person's birthday.
     * @param gender The gender of the ru.salnikov.entities.Person
     * @param division The division of the ru.salnikov.entities.Person working at
     */
    public Person(Integer id, String firstName, String lastName, LocalDate birthdate, Gender gender, IDivision division) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.division = division;
    }

    public Person() {

    }

    public static Person createEmptyPerson() {
        return new Person(0, "firstName", "lastName", LocalDate.now(), Gender.MALE, new Division(0, "divisionName"));
    }
    /**
     * @return The ID of the ru.salnikov.entities.Person
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id The unique number of the ru.salnikov.entities.Person
     * @return True if ID is bigger than 0, False otherwise
     */
    public void setId(Integer id) {
        if (id != null && id >= 0) {
            this.id = id;
        }
        else {
            this.id = 0;
        }
        LabFactory labFactory = new LabFactory();
    }

    /**
     * @return The surname of the ru.salnikov.entities.Person
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName The surname of the ru.salnikov.entities.Person
     * @return True if surname isn't empty or contains only white space codepoints, otherwise false
     */
    public void setLastName(String lastName) {
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = lastName;
        }
        else {
            this.lastName = "LastName";
        }
    }

    /**
     * @return The name of the ru.salnikov.entities.Person
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName The name of the ru.salnikov.entities.Person
     * @return True if name isn't empty or contains only white space codepoints, otherwise false
     */
    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName;
        }
        else {
            this.firstName = "FirstName";
        }
    }

    /**
     * @return The date of ru.salnikov.entities.Person's birthdate
     */
    public LocalDate getBirthdate() {
        return birthdate;
    }
    /**
     * @param birthdate The LocalDate object, representing birthday
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * @return The gender of the ru.salnikov.entities.Person
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender The gender of the ru.salnikov.entities.Person
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @param gender The gender of the ru.salnikov.entities.Person
     */
    public void setGender(String gender) {
        if (gender.trim().toLowerCase().equals("female")) {
            this.gender = Gender.FEMALE;
        }
        else if (gender.trim().toLowerCase().equals("male")) {
            this.gender = Gender.MALE;
        }
        else {
            this.gender = null;
        }
    }

    /**
     * @return The division of the ru.salnikov.entities.Person working at
     */
    public IDivision getDivision() {
        return division;
    }

    /**
     * @param division The division of the ru.salnikov.entities.Person working at
     */
    public void setDivision(IDivision division) {
        this.division = division;
    }

    /**
     * @return BigDecimal value, representing ru.salnikov.entities.Person's salary
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * @param salary BigDecimal value (bigger or equals to 0), representing ru.salnikov.entities.Person's salary
     */
    public void setSalary(BigDecimal salary) {
        if (salary != null && new BigDecimal(0).compareTo(salary) <= 0) {
            this.salary = salary;
        }
        else {
            this.salary = new BigDecimal(0);
        }
    }

    /**
     * @return return's the amount of years between birthdate and now
     */
    public Integer getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(birthdate, person.birthdate) &&
                gender == person.gender &&
                Objects.equals(division, person.division);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthdate, gender, division);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", division=" + division +
                ", salary=" + salary +
                "} +\r\n";
    }
}
