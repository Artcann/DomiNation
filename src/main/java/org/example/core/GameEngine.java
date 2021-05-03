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

    private static List<Domino[]> sortedDeck = null;

    private Player currentPlayer = null;

    private final ArrayList<Player> players = new ArrayList<>();
    private List<Domino[]> deck = new LinkedList<>();
    private final List<King> kings = new ArrayList<>();

    private final List<Domino[]> table = new ArrayList<>();



    public void newGame(int nbPlayers) {
        List<String[]> csvRaw = CsvParser.readCSV("dominos.csv");

        for(String[] dominos : csvRaw) {
            Domino[] domino = new Domino[2];
            domino[0] = new Domino(dominos[1], Integer.parseInt(dominos[0]), Integer.parseInt(dominos[4]));
            domino[1] = new Domino(dominos[3], Integer.parseInt(dominos[2]), Integer.parseInt(dominos[4]));
            this.deck.add(domino);
        }

        sortedDeck = new ArrayList<>(deck);

        Collections.shuffle(this.deck);
        this.deck = this.deck.subList(0, nbPlayers * 12);

        for (int i = 0; i<nbPlayers; i++) {
            this.players.add(new Player());
        }

        this.currentPlayer = players.get(0);

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

        Collections.shuffle(kings);

        //DEBUG ONLY
        Integer[] kingsPosition = new Integer[3];
        for(int i = 0; i<3; i++) {
            kingsPosition[i] = this.table.get(i)[0].getNumber();
        }

        for(int i = 0; i<kings.size(); i++) {
            kings.get(i).setPosition(sortedDeck.get(kingsPosition[i] - 1));
        }

        //TODO: Faire une méthode pour la pioche
        for(int i = 0; i<kings.size(); i++) {
            this.table.add(this.deck.remove(0));
        }

        table.sort(new DominoSorter());

        for(Domino[] d : this.table) {
            logger.debug(Arrays.toString(d));
        }

        kings.sort(new KingSorter());

        for(int i = 0; i<8; i++) {
            logger.debug(this.currentPlayer.toString());
            nextPlayer();
        }

    }
    

    private boolean isGameFinished() {
        return true;
    }

    private void nextPlayer() {
        this.currentPlayer = this.players.get((this.players.indexOf(this.currentPlayer) + 1) % (this.players.size()));
    }

}
