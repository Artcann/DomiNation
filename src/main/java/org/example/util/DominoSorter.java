package org.example.util;

import org.example.core.Domino;

import javax.inject.Singleton;
import java.util.Comparator;

@Singleton
public class DominoSorter implements Comparator<Domino[]> {

    @Override
    public int compare(Domino[] o1, Domino[] o2) {
        return Integer.compare(o1[0].getNumber(), o2[0].getNumber());
    }
}
