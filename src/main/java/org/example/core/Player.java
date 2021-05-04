package org.example.core;

import javax.inject.Inject;

public class Player {

    private Board board;

    private Castle castle;

    private static int nbInstances = 0;

    public Player(Castle castle, Board board) {
        this.castle = castle;
        this.board = board;

        this.castle.setColor(Color.values()[nbInstances]);
        this.board.initBoard(this.castle);

        nbInstances++;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public Color getColor() {
        return this.castle.getColor();
    }

    public String toString() {
        return this.castle.getColor().toString();
    }

}
