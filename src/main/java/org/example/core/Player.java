package org.example.core;

public class Player {

    private static int nbInstances = 0;
    protected final Board board;
    protected final Castle castle;
    protected final GameEngine gameEngine;

    public Player(Castle castle, Board board, GameEngine gameEngine) {
        this.castle = castle;
        this.board = board;
        this.gameEngine = gameEngine;

        this.castle.setColor(Color.values()[nbInstances]);
        this.board.initBoard(this.castle);

        nbInstances++;
    }

    public static void resetInstances() {
        nbInstances = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Color getColor() {
        return this.castle.getColor();
    }

    public String toString() {
        return this.castle.getColor().toString();
    }

}
