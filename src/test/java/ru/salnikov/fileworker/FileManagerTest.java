package ru.salnikov.fileworker;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.junit.Assert.*;

public class FileManagerTest {

    @Test
    public void loadInjectionFromFile() throws FileNotFoundException, ClassNotFoundException {
        Map<Class, Class> classMap = FileManager.LoadInjectionFromFile("C:\\Users\\Denvormine\\IdeaProjects\\task_1\\src\\main\\resources\\injector.cfg");
    }
}