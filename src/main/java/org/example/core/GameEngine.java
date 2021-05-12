package org.example.core;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.easydi.EasyDI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.example.util.*;

import java.util.*;

import javax.inject.Singleton;

@Singleton
public class GameEngine implements ViewModel {

    private static final Logger logger = LogManager.getLogger(GameEngine.class);

    private static List<Domino[]> sortedDeck;

    private Player currentPlayer;

    private final ArrayList<Player> players = new ArrayList<>();
    private List<Domino[]> deck = new ArrayList<>();
    private final List<King> kings = new ArrayList<>();

    private final List<Domino[]> table = new ArrayList<>();

    private final EasyDI easyDI = new EasyDI();

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
            this.players.add(easyDI.getInstance(Player.class));
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

        //TODO: Faire une méthode piocher un domino
        for(int i = 0; i<kings.size(); i++) {
            this.table.add(this.deck.remove(0));
        }

        table.sort(new DominoSorter());

        for(Domino[] d : this.table) {
            logger.debug(Arrays.toString(d));
        }

        kings.sort(new KingSorter());

        Integer[][] positionTest = {{3,4}, {3,5}};
        Integer[][] positionTest2 = {{2,4}, {2,5}};
        Integer[][] positionTest3 = {{1,4}, {1,5}};
        Integer[][] positionTest4 = {{0,4}, {0,5}};
        Integer[][] positionTest5 = {{5,4}, {5,5}};

        players.get(0).getBoard().placeDomino(positionTest, sortedDeck.get(4));
        players.get(0).getBoard().placeDomino(positionTest2, sortedDeck.get(28));
        players.get(0).getBoard().placeDomino(positionTest3, sortedDeck.get(5));
        players.get(0).getBoard().placeDomino(positionTest4, sortedDeck.get(24));
        players.get(0).getBoard().placeDomino(positionTest5, sortedDeck.get(1));
        logger.debug(players.get(0).getBoard().computeScore());

        logger.debug(Arrays.toString(players.get(0).getBoard().getBorder()));

        logger.debug(players.get(0).getBoard().toString());

    }


    private void nextPlayer() {
        this.currentPlayer = this.players.get((this.players.indexOf(this.currentPlayer) + 1) % (this.players.size()));
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public List<Domino[]> getDeck() {
        return deck;
    }

    public void setDeck(List<Domino[]> deck) {
        this.deck = deck;
    }

    public List<King> getKings() {
        return kings;
    }

    public List<Domino[]> getTable() {
        return table;
    }
}
