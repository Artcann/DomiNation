package org.example.core;

import de.saxsys.mvvmfx.ViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.CsvParser;

import java.util.ArrayDeque;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class GameEngine implements ViewModel {

    private final static Logger logger = LogManager.getLogger(GameEngine.class);

    private Player[] players;
    private ArrayDeque<Domino> deck;

    public void run() {
        Board board = new Board();
        board.initialize();

        List<String> csvRaw = CsvParser.readCSV("dominos.csv");
        logger.debug(csvRaw.toString());
    }

}
