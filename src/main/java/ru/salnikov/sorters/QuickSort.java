package ru.salnikov.sorters;

import ru.vsu.lab.entities.IPerson;
import ru.vsu.lab.repository.IRepository;

import java.util.Comparator;


public class QuickSort implements ISorter {
    protected void quickSort(IRepository repository, int start, int end, Comparator comparator) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && comparator.compare(repository.get(i), repository.get(cur)) <= 0) {
                i++;
            }
            while (j > cur && comparator.compare(repository.get(cur), repository.get(j)) <= 0) {
                j--;
            }
            if (i < j) {
                IPerson temp = (IPerson)repository.get(i);
                repository.set(i, repository.get(j));
                repository.set(j, temp);
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        quickSort(repository, start, cur, comparator);
        quickSort(repository, cur + 1, end, comparator);
    }

    public void sort(IRepository repository, Comparator comparator) {
        int count = getCount(repository);
        quickSort(repository, 0, 9, comparator);
    }
}
