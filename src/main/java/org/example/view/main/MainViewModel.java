package org.example.view.main;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.core.Board;
import org.example.core.Color;
import org.example.core.GameEngine;
import org.example.core.Tile;

import javax.inject.Inject;

public class MainViewModel implements ViewModel {

    private ObservableList<Tile> observableBoard = FXCollections.observableArrayList();

    private GameEngine gameEngine;

    private StringProperty currentPlayer = new SimpleStringProperty();

    public MainViewModel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        currentPlayer.set(gameEngine.getCurrentPlayer().getColor().toString());
    }

    public ObservableList<Tile> getObservableBoard() {
        return observableBoard;
    }

    public void initBoard() {
        Tile[][] board = this.gameEngine.getCurrentPlayer().getBoard().getBoardArr();

        for(Tile[] tiles : board) {
            observableBoard.addAll(tiles);
        }
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
