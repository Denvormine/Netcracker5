package ru.salnikov.sorters;

import ru.vsu.lab.repository.IRepository;

import java.util.Comparator;

public class InsertionSort implements ISorter {
    public void sort(IRepository repository, Comparator comparator) {
        int n = 10;
        int i, j;
        Object key;
        for (i = 1; i < n; i++)
        {
            key = repository.get(i);
            j = i - 1;
            while (j >= 0 && comparator.compare(repository.get(j), key) > 0)
            {
                repository.set(j + 1, repository.get(j));
                j = j - 1;
            }
            repository.set(j + 1, key);
        }
    }
}
