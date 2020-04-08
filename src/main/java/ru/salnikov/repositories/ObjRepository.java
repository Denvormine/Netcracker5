package ru.salnikov.repositories;

import ru.salnikov.annotations.ForInjection;
import ru.salnikov.sorters.ISorter;
import ru.vsu.lab.repository.IRepository;
import java.util.*;
import java.util.function.Predicate;


public class ObjRepository<T> implements IRepository<T> {
    /**
     * The size of the array, when it's creating.
     */
    private final int defaultSize = 10;
    /**
     * The collection, that are using to create dynamic array for ru.salnikov.entities.Person.
     */
    private T[] array;
    /**
     * The amount of objects in the array.
     */
    private int count;
    @ForInjection
    private ISorter sorter;
    @ForInjection
    public Comparator<T> comparator;

    public int getCount() {
        return count;
    }
    /**
     * Creates a new array.
     */
    public ObjRepository() {
        array = (T[]) new Object[defaultSize];
        count = 0;
    }

    /**
     * Checks, if count equals to array's length and if it's true, than creates new array with length 1.5 times bigger
     * than the length of original array and copies all objects from it to new one and it's replaces original array.
     */
    protected void increaseLength() {
        if (count != array.length) {
            return;
        }
        T[] tempArr = (T[])new Object[(int)(array.length * 1.5)];
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
        T[] tempArr = (T[])new Object[count];
        System.arraycopy(array, 0, tempArr, 0, count);
        array = tempArr;
    }

    /**
     * Adds person to the end of the array and increases count.
     * @param person - The object of ru.salnikov.entities.Person
     * @return True, if person was added, False otherwise
     */
    public void add(T person) {
        if (person == null) {
            return;
        }
        increaseLength();
        array[count++] = person;
    }

    public void add(int index, T person) {
        set(index, person);
    }
    /**
     * @param index - The index of array, that person would take place of
     * @param person - The object of ru.salnikov.entities.Person
     * @return True, if index is correct and person isn't null
     */
    public T set(int index, T person) {
        if (index >= count || person == null) {
            return null;
        }
        T tempPerson = array[index];
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
    }

    /** Removes the object on index position, decreases count and length (if needed).
     * @param index - Position of the object, that needed to be removed
     */
    protected void removeItem(int index) {
        moveItems(index);
        //count--;
        decreaseLength();
    }

    /**
     * Removes person from array and returns it.
     * @param person An object of ru.salnikov.entities.Person.
     * @return Removed object of ru.salnikov.entities.Person.
     */
    public Object deletePerson(Object person) {
        if (person == null) {
            return null;
        }
        for (int i = 0; i < count; i++) {
            if (array[i].equals(person)) {
                Object resPerson = array[i];
                removeItem(i);
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
    public T delete(int index) {
        if (index >= count) {
            return null;
        }
        T resPerson = array[index];
        removeItem(index);
        return resPerson;
    }

    /**
     * Gets person, that takes place on index.
     * @param index The index of person, that is needed to be gotten
     * @return The object of ru.salnikov.entities.Person
     */
    public T get(int index) {
        if (index >= count) {
            return null;
        }
        return array[index];
    }

    public List<T> toList() {
        if (count == 0) {
            return null;
        }
        LinkedList<T> personList = new LinkedList<T>();
        for (int i = 0; i < count; i++) {
            personList.add(array[i]);
        }
        return personList;
    }

    public void sortBy(Comparator<T> comparator) {
        if (comparator != null) {
            sorter.sort(this, comparator);
        }
    }
    public IRepository searchBy(Predicate<T> condition) {
        ObjRepository repository = new ObjRepository();
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
        ObjRepository that = (ObjRepository) o;
        return defaultSize == that.defaultSize &&
                count == that.count &&
                Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(defaultSize, count);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return "ru.salnikov.repoitories.ObjRepository{" +
                "defaultSize=" + defaultSize +
                ", array=" + Arrays.toString(array) +
                ", count=" + count +
                '}';
    }
}
