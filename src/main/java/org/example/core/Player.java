package org.example.core;

public class Player {

    private Board board;
    private Castle castle;

    private static int nbInstances = 0;

    public Player() {
        this.board = new Board();
        this.castle = new Castle(Color.values()[nbInstances]);
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

}
