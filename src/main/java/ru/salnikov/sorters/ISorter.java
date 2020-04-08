package ru.salnikov.sorters;

import ru.vsu.lab.repository.IRepository;

import java.util.Comparator;

public interface ISorter {
    void sort(IRepository repository, Comparator comparator);
    default int getCount(IRepository repository) {
        int count = 0;
        try {
            repository.get(count);
            count++;
        }
        catch (Exception exception) {
            return count;
        }
        return count;
    }
}
