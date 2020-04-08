package ru.salnikov.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.salnikov.repositories.PersonDynamicArray;

public class InjectorException extends Exception {

    public InjectorException(Exception exception) {
        super(exception);
    }
}
