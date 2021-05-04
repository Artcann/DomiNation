package org.example.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.ReadPropertyFile;

import java.io.*;

public class Board {

    private static final Logger logger = LogManager.getLogger(Board.class);

    private Tile[][] board;
    private final int size;
    private int topBorder;
    private int bottomBorder;
    private int leftBorder;
    private int rightBorder;

    public Board() throws IOException {
        ReadPropertyFile properties = new ReadPropertyFile();
        this.size = Integer.parseInt(properties.getPropValues("size"));
        this.topBorder = size - 1;
        this.bottomBorder = size - 1;
        this.leftBorder = size - 1;
        this.rightBorder = size - 1;
    }


    public void initBoard(Castle castle) {
        this.board = new Tile[(2*this.size)-1][(2*this.size)-1];
        this.board[this.size-1][this.size-1] = castle;
    }

    public void placeDomino(Integer[][] positions, Domino[] domino) {
        if(isValidMove(positions)) {
            this.board[positions[0][0]][positions[0][1]] = domino[0];
            this.board[positions[1][0]][positions[1][1]] = domino[1];
        } else {
            logger.error("La case n'est pas vide");
        }
    }

    public boolean isValidMove(Integer[][] move) {
        return isTileEmpty(move[0]) && isTileEmpty(move[1]);
    }

    public boolean isTileEmpty(Integer[] cell) {
        return board[cell[0]][cell[1]] == null;
    }

    public boolean isDominoInside(Integer[] cell) {
        return true;
    }

    public String toString() {

        StringBuilder strBoard = new StringBuilder();

        strBoard.append("\n");

        for (Tile[] tiles : board) {
            for (int j = 0; j < board.length; j++) {
                if (tiles[j] != null) {
                    strBoard.append(tiles[j].toString());
                } else {
                    strBoard.append("0");
                }
                strBoard.append(" | ");
            }
            strBoard.append("\n");
        }

        return strBoard.toString();
    }

}
