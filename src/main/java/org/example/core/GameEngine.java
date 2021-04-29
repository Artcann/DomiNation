package org.example.core;

import de.saxsys.mvvmfx.ViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.example.util.*;

import java.util.*;

import javax.inject.Singleton;

@Singleton
public class GameEngine implements ViewModel {

    private final static Logger logger = LogManager.getLogger(GameEngine.class);

    private final ArrayList<Player> players = new ArrayList<Player>();
    private List<Domino[]> deck = new LinkedList<>();
    private List<King> kings = new ArrayList<>();

    private List<Domino[]> table = new ArrayList<>();

    public GameEngine(int nbPlayers) {
        List<String[]> csvRaw = CsvParser.readCSV("dominos.csv");

        for(String[] dominos : csvRaw) {
            Domino[] domino = new Domino[2];
            domino[0] = new Domino(dominos[1], Integer.parseInt(dominos[0]), Integer.parseInt(dominos[4]));
            domino[1] = new Domino(dominos[3], Integer.parseInt(dominos[2]), Integer.parseInt(dominos[4]));
            this.deck.add(domino);
        }
        Collections.shuffle(this.deck);
        this.deck = this.deck.subList(0, nbPlayers * 12);

        for (int i = 0; i<nbPlayers; i++) {
            this.players.add(new Player());
        }

        if (nbPlayers>2) {
            for(Player p : players) {
                kings.add(new King(p.getColor()));
            }
        } else {
            for(int i = 0; i<2; i++) {
                kings.add(new King(Color.values()[0]));
                kings.add(new King(Color.values()[1]));
            }
        }

        for(int i = 0; i<kings.size(); i++) {
            this.table.add(this.deck.remove(0));
        }

        table.sort(new DominoSorter());

        logger.debug(this.deck.size());
        logger.debug(this.players.toString());

    }

    public void run() {

    }

}
