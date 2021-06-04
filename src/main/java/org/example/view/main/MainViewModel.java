package org.example.view.main;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.Starter;
import org.example.core.*;
import org.example.view.menu.MenuView;

public class MainViewModel implements ViewModel {

    private final ObservableList<Tile> observableBoard = FXCollections.observableArrayList();

    private final SimpleListProperty<Domino[]> observableTable = new SimpleListProperty<>();

    private final GameEngine gameEngine;

    private final StringProperty currentPlayer = new SimpleStringProperty();

    private final StringProperty playerScore = new SimpleStringProperty();



    public MainViewModel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        currentPlayer.set(gameEngine.getCurrentPlayer().getColor().toString());
        this.observableTable.bindBidirectional(gameEngine.getTableProperty());
        gameEngine.getDeck().addListener((ListChangeListener<Domino[]>) change -> {
            if(gameEngine.getDeck().isEmpty()) {
                ViewTuple viewTuple = FluentViewLoader.fxmlView(MenuView.class).load();

                Parent root = viewTuple.getView();

                Starter.getPrimaryStage().setScene(new Scene(root));
            }
        });

        updateScore();
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
        }
        return false;
    }

    public Color getColor() {
        return gameEngine.getCurrentPlayer().getColor();
    }

    public void nextPlayer() {
        observableTable.remove(0);
        gameEngine.nextPlayer();
        updateScore();
        updateBoard();
        currentPlayer.set(gameEngine.getCurrentPlayer().getColor().toString());
    }

    public StringProperty currentPlayerProperty() {
        return currentPlayer;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
