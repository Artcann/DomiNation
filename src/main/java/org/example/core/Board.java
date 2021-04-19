package org.example.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Board {

    private static final Logger logger = LogManager.getLogger(Board.class);

    private Tile[][] board;
    private int size;

    public void initialize() {
        this.board = new Tile[this.size][this.size];
    };


    public String toString() {

        StringBuilder strBoard = new StringBuilder();

        for (Tile[] tiles : board) {
            for (int j = 0; j < board.length; j++) {
                strBoard.append(tiles[j]);
            }
            strBoard.append("\n");
        }

        return strBoard.toString();
    }

}
