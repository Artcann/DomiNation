package org.example.util;

import org.example.core.Domino;
import org.example.core.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;
import java.util.Comparator;

@Singleton
public class playerSorter implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return Integer.compare(o1.getBoard().computeScore(), o2.getBoard().computeScore());
    }
}
