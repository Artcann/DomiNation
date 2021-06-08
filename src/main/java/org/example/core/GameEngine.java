package org.example.core;

import eu.lestard.easydi.EasyDI;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.example.util.*;

import java.util.*;

public class GameEngine {

    private static final Logger logger = LogManager.getLogger(GameEngine.class);

    private final EasyDI easyDI = new EasyDI();

    private Player currentPlayer;

    private ArrayList<Player> players;
    private ObservableList<Domino[]> deck;

    private ObservableList<Domino[]> table;



    public GameEngine() {
        logger.debug("GameEngine Created");
    }



    public void newGame(int nbPlayers) {
        logger.debug("New Game");
        Player.resetInstances();

        this.players = new ArrayList<>();
        List<King> kings = new ArrayList<>();
        this.deck = FXCollections.observableArrayList();

        this.table = FXCollections.observableArrayList();


        List<String[]> csvRaw = CsvParser.readCSV("dominos.csv");

        for(String[] dominos : csvRaw) {
            Domino[] domino = new Domino[2];
            domino[0] = new Domino(dominos[1], Integer.parseInt(dominos[0]), Integer.parseInt(dominos[4]), 0);
            domino[1] = new Domino(dominos[3], Integer.parseInt(dominos[2]), Integer.parseInt(dominos[4]), 1);
            this.deck.add(domino);
        }

        List<Domino[]> sortedDeck = new ArrayList<>(deck);

        Collections.shuffle(this.deck);
        this.deck = FXCollections.observableArrayList(this.deck.subList(0, nbPlayers * 12));

        this.deck.addListener((ListChangeListener<Domino[]>) change -> {
            if(deck.isEmpty()) {
                logger.debug("Le jeu est fini");
            }
        });

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

        for(int i = 0; i< kings.size(); i++) {
            this.table.add(this.deck.remove(0));
        }

        Collections.shuffle(kings);

        //DEBUG ONLY
        Integer[] kingsPosition = new Integer[3];
        for(int i = 0; i<3; i++) {
            kingsPosition[i] = this.table.get(i)[0].getNumber();
        }

        for(int i = 0; i< kings.size(); i++) {
            kings.get(i).setPosition(sortedDeck.get(kingsPosition[i] - 1));
        }

        //TODO: Faire une méthode piocher un domino
        for(int i = 0; i< kings.size(); i++) {
            this.table.add(this.deck.remove(0));
        }

        table.sort(new DominoSorter());

        kings.sort(new KingSorter());


    }

    public void nextPlayer() {
        this.currentPlayer = this.players.get((this.players.indexOf(this.currentPlayer) + 1) % (this.players.size()));
        this.table.add(this.deck.remove(0));
        this.table.sort(new DominoSorter());
    }

    // Getters and Setters

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ObservableList<Domino[]> getDeck() {
        return deck;
    }

    public ObservableList<Domino[]> getTable() {
        return table;
    }

    public Property<ObservableList<Domino[]>> getTableProperty() {
        return new SimpleListProperty<>(this.table);
    }
}
