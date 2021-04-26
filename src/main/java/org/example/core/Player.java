package org.example.core;

public class Player {

    private final String[] colors = {"PINK", "YELLOW", "GREEN", "BLUE"};

    private Board board;
    private Castle castle;
    private int[] kingPosition;

    private static int nbInstances = 0;

    public Player() {
        this.board = new Board();
        this.castle = new Castle(colors[nbInstances]);
    }

}
