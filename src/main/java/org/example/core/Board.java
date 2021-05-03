package org.example.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Board {

    private static final Logger logger = LogManager.getLogger(Board.class);

    private Tile[][] board;
    private int size = 5;

    public Board(Castle castle) {
        this.board = new Tile[(2*this.size)-1][(2*this.size)-1];
        this.board[this.size-1][this.size-1] = castle;
    };


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
