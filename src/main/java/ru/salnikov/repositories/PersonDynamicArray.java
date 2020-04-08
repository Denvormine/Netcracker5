package ru.salnikov.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.salnikov.annotations.ForInjection;
import ru.salnikov.entities.Person;
import ru.salnikov.sorters.ISorter;
import ru.vsu.lab.entities.IPerson;
import ru.vsu.lab.repository.IPersonRepository;
import ru.vsu.lab.repository.IRepository;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;



/** The dynamic array for the class ru.salnikov.entities.Person.
 * @author Denvormine
 *
 */
@XmlType(name = "PersonDynamicArray")
@XmlRootElement
@Component
public class PersonDynamicArray implements IPersonRepository, Serializable {

    @XmlTransient
    private final Logger logger = LoggerFactory.getLogger(PersonDynamicArray.class);
    /**
     * The size of the array, when it's creating.
     */
    private final int defaultSize = 10;
    /**
     * The collection, that are using to create dynamic array for ru.salnikov.entities.Person.
     */
    @XmlAnyElement(lax = true)
    private IPerson[] array;
    /**
     * The amount of objects in the array.
     */
    @XmlElement
    private int count;

    @ForInjection
    @XmlAnyElement
    private ISorter sorter;

    public int getCount() {
        return count;
    }
    /**
     * Creates a new array.
     */
    public PersonDynamicArray() {
        array = new Person[defaultSize];
        count = 0;
        logger.trace("PersonDynamicArray was created");
    }

    /**
     * Checks, if count equals to array's length and if it's true, than creates new array with length 1.5 times bigger
     * than the length of original array and copies all objects from it to new one and it's replaces original array.
     */
    protected void increaseLength() {
        if (count != array.length) {
            return;
        }
        Person[] tempArr = new Person[(int)(array.length * 1.5)];
        logger.trace("Increasing length of array from " + array.length + " to " + array.length * 1.5);
        System.arraycopy(array, 0, tempArr, 0, array.length);
        array = tempArr;

    }

    /**
     * Checks, if count is 2 times lower than length and if it's true, than creates new array with length 2 times smaller
     * than the length of original array and copies all objects from it to new one and it's replaces original array.
     */
    protected void decreaseLength() {
        if (count * 2 > array.length || array.length == defaultSize) {
            return;
        }
        Person[] tempArr = new Person[count];
        logger.trace("Decreasing length of array from " + array.length + " to " + count);
        System.arraycopy(array, 0, tempArr, 0, count);
        array = tempArr;
    }

    /**
     * Adds person to the end of the array and increases count.
     * @param person - The object of ru.salnikov.entities.Person
     * @return True, if person was added, False otherwise
     */
    public void add(IPerson person) {
        if (person == null) {
            logger.warn("Null pointer was tried to be added in array");
            return;
        }
        increaseLength();
        array[count++] = person;
        logger.trace("Adding person with id=" + person.getId());
    }
    public void add(int index, IPerson person) {
        set(index, person);
    }
    /**
     * @param index - The index of array, that person would take place of
     * @param person - The object of ru.salnikov.entities.Person
     * @return True, if index is correct and person isn't null
     */
    public IPerson set(int index, IPerson person) {
        if (index >= count || person == null) {
            return null;
        }
        IPerson tempPerson = array[index];
        array[index] = person;
        return tempPerson;
    }

    /**
     * Moves all object from right to left by 1 position starting from index,
     * replaces object on array[index] and decreases count.
     * @param index - Starting position for moving
     */
    protected void moveItems(int index) {
        System.arraycopy(array, index + 1, array, index, count - index - 1);
        array[count - 1] = null;
        count--;
        logger.trace("Moving items in array");
    }

    /** Removes the object on index position, decreases count and length (if needed).
     * @param index - Position of the object, that needed to be removed
     */
    protected void removeItem(int index) {
        moveItems(index);
        decreaseLength();
    }

    /**
     * Removes person from array and returns it.
     * @param person An object of ru.salnikov.entities.Person.
     * @return Removed object of ru.salnikov.entities.Person.
     */
    public IPerson deletePerson(IPerson person) {
        if (person == null) {
            logger.warn("Null pointer was tried to be deleted from array");
            return null;
        }
        for (int i = 0; i < count; i++) {
            if (array[i].equals(person)) {
                IPerson resPerson = array[i];
                removeItem(i);
                logger.trace("Person with id=" + resPerson.getId() + " was deleted from array");
                return resPerson;
            }
        }
        return null;
    }

    /**
     * Removes person that takes place on array[index] from array and returns it.
     * @param index The index of the person, that needed to be removed.
     * @return Removed ru.salnikov.entities.Person.
     */
    public IPerson delete(int index) {
        if (index >= count || index < 0) {
            logger.warn("Index for deleting was out of range: " + index);
            return null;
        }
        IPerson resPerson = array[index];
        removeItem(index);
        logger.trace("Person with id=" + resPerson.getId() + " was deleted from array");
        return resPerson;
    }

    /**
     * Gets person, that takes place on index.
     * @param index The index of person, that is needed to be gotten
     * @return The object of ru.salnikov.entities.Person
     */
    public IPerson get(int index) {
        if (index >= count || index < 0) {
            logger.warn("Index for getting was out of range: " + index);
            return null;
        }
        logger.trace("Person with id=" + array[index].getId() + " was got from array");
        return array[index];
    }

    public List<IPerson> toList() {
        if (count == 0) {
            return null;
        }
        LinkedList<IPerson> personList = new LinkedList<IPerson>();
        for (int i = 0; i < count; i++) {
            personList.add(array[i]);
        }
        return personList;
    }


    public void sortBy(Comparator<IPerson> comparator) {
        sorter.sort(this, comparator);
        logger.trace("Array was sorted");
    }
    public IRepository searchBy(Predicate<IPerson> condition) {
        PersonDynamicArray repository = new PersonDynamicArray();
        for (int i = 0; i < count; i++) {
            if (condition.test(array[i])) {
                repository.add(array[i]);
            }
        }
        if (repository.getCount() == 0) {
            return null;
        }
        return repository;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDynamicArray array1 = (PersonDynamicArray) o;
        return defaultSize == array1.defaultSize &&
                count == array1.count &&
                Arrays.equals(array, array1.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(defaultSize, count);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return "ru.salnikov.repoitories.PersonDynamicArray{" +
                "defaultSize=" + defaultSize +
                ", array=" + Arrays.toString(array) +
                ", count=" + count +
                '}';
    }
}