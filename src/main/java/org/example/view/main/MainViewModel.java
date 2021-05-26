package org.example.view.main;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.core.*;

import javax.inject.Inject;

public class MainViewModel implements ViewModel {

    private ObservableList<Tile> observableBoard = FXCollections.observableArrayList();

    private SimpleListProperty<Domino[]> observableTable = new SimpleListProperty<>();

    private GameEngine gameEngine;

    private StringProperty currentPlayer = new SimpleStringProperty();

    public MainViewModel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        currentPlayer.set(gameEngine.getCurrentPlayer().getColor().toString());
        this.observableTable.bindBidirectional(gameEngine.getTableProperty());
    }

    public ObservableList<Tile> getObservableBoard() {
        return observableBoard;
    }

    public SimpleListProperty<Domino[]> getObservableTable() {
        return observableTable;
    }

    public void initBoard() {
        Tile[][] board = this.gameEngine.getCurrentPlayer().getBoard().getBoardArr();

        for(int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                observableBoard.add(j+(9*i), board[i][j]);
            }
        }
    }

    public void updateBoard() {
        Tile[][] board = this.gameEngine.getCurrentPlayer().getBoard().getBoardArr();

        for(int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                observableBoard.set(j+(9*i), board[i][j]);
            }
        }
    }

    public boolean makeMove(Integer[][] move, Domino[] domino) {
        return gameEngine.getCurrentPlayer().getBoard().placeDomino(move, domino);
    }

    public Color getColor() {
        return gameEngine.getCurrentPlayer().getColor();
    }

    public void nextPlayer() {
        gameEngine.nextPlayer();
        currentPlayer.set(gameEngine.getCurrentPlayer().getColor().toString());
    }

    public StringProperty currentPlayerProperty() {
        return currentPlayer;
    }
}
