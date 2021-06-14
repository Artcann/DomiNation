package org.example.view.main;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.Starter;
import org.example.core.*;
import org.example.view.menu.MenuView;

import javax.inject.Inject;

public class MainViewModel implements ViewModel {

    private ObservableList<Tile> observableBoard = FXCollections.observableArrayList();

    private SimpleListProperty<Domino[]> observableTable = new SimpleListProperty<>();

    private GameEngine gameEngine;

    private StringProperty currentPlayer = new SimpleStringProperty();

    private final StringProperty playerScore = new SimpleStringProperty();

    public MainViewModel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        currentPlayer.set(gameEngine.getCurrentPlayer().getColor().toString());
        this.observableTable.bindBidirectional(gameEngine.getTableProperty());
    }

    public String getPlayerScore() {
        return playerScore.get();
    }

    public StringProperty playerScoreProperty() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore.set(String.valueOf(playerScore));
    }

    public void updateScore() {
        this.playerScore.set(String.valueOf(gameEngine.getCurrentPlayer().getBoard().computeScore()));
    }

    public boolean endGame() {
        return gameEngine.getDeck().size() == 0;

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
        if(gameEngine.getCurrentPlayer().getBoard().placeDomino(move, domino)) {
            gameEngine.getTable().remove(domino);
            return true;
        };
        return false;
    }

    public Color getColor() {
        return gameEngine.getCurrentPlayer().getColor();
    }

    public void nextPlayer() {
        if(endGame()) {
            ViewTuple viewTuple = FluentViewLoader.fxmlView(MenuView.class).load();

            Parent root = viewTuple.getView();

            Scene endScene = new Scene(root);

            Starter.getPrimaryStage().setScene(endScene);

            return;
        }
        gameEngine.nextPlayer();
        currentPlayer.set(gameEngine.getCurrentPlayer().getColor().toString());
    }

    public StringProperty currentPlayerProperty() {
        return currentPlayer;
    }
}
