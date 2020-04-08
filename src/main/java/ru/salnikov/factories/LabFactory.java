package ru.salnikov.factories;

import ru.salnikov.entities.Division;
import ru.salnikov.entities.Person;
import ru.salnikov.repositories.ObjRepository;
import ru.salnikov.repositories.PersonDynamicArray;
import ru.vsu.lab.entities.IDivision;
import ru.vsu.lab.entities.IPerson;
import ru.vsu.lab.factory.ILabFactory;
import ru.vsu.lab.repository.IPersonRepository;
import ru.vsu.lab.repository.IRepository;

public class LabFactory implements ILabFactory {
    /**
     * Create {@link IPerson} entity
     *
     * @return {@link IPerson} entity
     */
    //Use a simple code like return new ru.salnikov.entities.Person()
    public IPerson createPerson() {
        return Person.createEmptyPerson();
    }

    /**
     * @return {@link IDivision} entity
     */
    //Use a simple code like return new ru.salnikov.entities.Division()
    public IDivision createDivision() {
        return new Division(0,"");
    }


    // If you don't know how to work with generic then skip it and work with
    // createPersonRepository() method
    /**
     * @return {@link IRepository<T>} entity
     */
    @Override
    public <T> IRepository<T> createRepository(Class<T> clazz) {
        return new ObjRepository<T>();
    }

    /**
     * @return {@link IPersonRepository} entity
     */
    //Currently use a simple code like return new PersonRepository()
    public IPersonRepository createPersonRepository() {
        return new PersonDynamicArray();
    }
}
