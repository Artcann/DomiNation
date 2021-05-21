package org.example.util;

import org.example.core.Domino;
import org.example.core.King;

import javax.inject.Singleton;
import java.util.Comparator;

@Singleton
public class KingSorter implements Comparator<King> {

    @Override
    public int compare(King o1, King o2) {
        return Integer.compare(o1.getDomino()[0].getNumber(), o2.getDomino()[0].getNumber());
    }
}
