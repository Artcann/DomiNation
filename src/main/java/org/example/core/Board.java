package org.example.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.ReadPropertyFile;

import java.io.*;
import java.util.*;

public class Board {

    private static final Logger logger = LogManager.getLogger(Board.class);

    private Tile[][] boardArr;
    private final int size;
    private int topBorder;
    private int bottomBorder;
    private int leftBorder;
    private int rightBorder;

    public Board() throws IOException {
        ReadPropertyFile properties = new ReadPropertyFile();
        this.size = (2 * Integer.parseInt(properties.getPropValues("size"))) - 1;
        //TODO Make this scalable
        this.topBorder = 2;
        this.bottomBorder = 6;
        this.leftBorder = 2;
        this.rightBorder = 6;
    }


    public void initBoard(Castle castle) {
        this.boardArr = new Tile[this.size][this.size];
        for(int i = 0; i < boardArr.length; i++) {
            for(int j = 0; j < boardArr.length; j++) {
                this.boardArr[i][j] = null;
            }
        }
        this.boardArr[(this.size+1) / 2 - 1][(this.size+1) / 2 - 1] = castle;
        Integer[] castlePosition = {4, 4};
        castle.setPosition(castlePosition);
    }

    public int computeScore() {
        Set<Tile> exploredTiles = new HashSet<>();
        int score = 0;
        for (Tile[] tiles : boardArr) {
            for (int j = 0; j < boardArr.length; j++) {
                if (tiles[j] != null && tiles[j].getClass() == Castle.class) {
                    exploredTiles.add(tiles[j]);
                } else if (tiles[j] != null && !exploredTiles.contains(tiles[j])) {
                    score += computeArea((Domino) tiles[j], exploredTiles);
                }

            }
        }
        return score;
    }

    public int computeArea(Domino startingNode, Set<Tile> exploredNode) {
        Deque<Domino> stack = new ArrayDeque<>();
        stack.push(startingNode);
        exploredNode.add(startingNode);
        var nbCrowns = 0;
        var sizeOfArea = 0;
        while(!stack.isEmpty()) {
            Domino current = stack.pop();
            exploredNode.add(current);
            if(current.getType().equals(startingNode.getType())) {
                nbCrowns += current.getNbCrown();
                sizeOfArea++;
                for(Tile domino : getNeighbors(current)) {
                    if(domino != null && domino.getClass() != Castle.class && !exploredNode.contains(domino)) {
                        stack.push((Domino) domino);
                    }
                }
            }
        }
        return nbCrowns * sizeOfArea;
    }

    public boolean placeDomino(Integer[][] positions, Domino[] domino) {
        try {
            if (isValidMove(positions, domino)) {
                this.boardArr[positions[0][0]][positions[0][1]] = domino[0];
                this.boardArr[positions[1][0]][positions[1][1]] = domino[1];

                domino[0].setPosition(positions[0]);
                updateBoardLimit(positions[0]);

                domino[1].setPosition(positions[1]);
                updateBoardLimit(positions[1]);
                return true;

            } else {
                logger.error("The move is not valid");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidMove(Integer[][] move, Domino[] domino) {
        return isDominoInside(move[0])
                && isDominoInside(move[1])
                && isTileEmpty(move[0])
                && isTileEmpty(move[1])
                && (hasSameTypeNeighbor(move[0], domino[0])
                || hasSameTypeNeighbor(move[1], domino[1]));
    }

    private boolean isTileEmpty(Integer[] cell) {
        return boardArr[cell[0]][cell[1]] == null;
    }

    private boolean isDominoInside(Integer[] cell) {
        if (!(cell[0] >= this.bottomBorder||
                cell[1] >= this.rightBorder ||
                cell[0] <= this.topBorder ||
                cell[1] <= this.leftBorder)) {
            updateBoardLimit(cell);
            return true;
        } else {
            return false;
        }
    }

    private void updateBoardLimit(Integer[] cell) {
        this.leftBorder = (cell[1] == this.leftBorder + 1 && this.rightBorder - this.leftBorder <= 7) ? this.leftBorder - 1 : this.leftBorder;
        this.rightBorder = (cell[1] == this.rightBorder - 1 && this.rightBorder - this.leftBorder <= 7) ? this.rightBorder + 1 : this.rightBorder;
        this.topBorder = (cell[0] == this.topBorder + 1 && this.bottomBorder - this.topBorder <= 7) ? this.topBorder - 1: this.topBorder;
        this.bottomBorder = (cell[0] == this.bottomBorder - 1 && this.bottomBorder - this.topBorder <= 7) ? this.bottomBorder + 1 : this.bottomBorder;

        if(this.topBorder == -2) {
            this.topBorder = -1;
            this.bottomBorder = 5;
        }
        if(this.bottomBorder == 10) {
            this.bottomBorder = 9;
            this.topBorder = 3;
        }
        if(this.leftBorder == -2) {
            this.leftBorder = -1;
            this.rightBorder = 5;
        }
        if(this.rightBorder == 10) {
            this.rightBorder = 9;
            this.leftBorder = 3;
        }




    }

    private boolean hasSameTypeNeighbor(Integer[] move, Domino domino) {

        domino.setPosition(move);

        List<Tile> neighbors = getNeighbors(domino);

        for(Tile neighbor : neighbors) {
            if(neighbor != null && (neighbor.getType().equals("Castle") || neighbor.getType().equals(domino.getType()))) return true;
        }
        domino.setPosition(null);
        return false;
    }

    private List<Tile> getNeighbors(Domino domino) {
        List<Tile> neighborsList = new ArrayList<>();
        Integer[] position = domino.getPosition();

        // Handle Corners
        if(position[0] == this.topBorder + 1 && position[1] == this.leftBorder + 1) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[0] == this.topBorder + 1 && position[1] == this.rightBorder - 1) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
        }
        else if(position[0] == this.bottomBorder - 1 && position[1] == this.leftBorder + 1) {
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[0] == this.bottomBorder - 1 && position[1] == this.rightBorder - 1) {
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
        }

        // Handle Borders
        else if(position[0] == this.topBorder + 1) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[0] == this.bottomBorder - 1) {
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
        }
        else if(position[1] == this.leftBorder + 1) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[1] == this.rightBorder - 1) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
        }

        //Handle Center
        else {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }

        return neighborsList;
    }

    public Integer[] getBorder() {
        Integer[] arr = new Integer[4];
        arr[0] = this.topBorder;
        arr[1] = this.rightBorder;
        arr[2] = this.bottomBorder;
        arr[3] = this.leftBorder;
        return arr;
    }

    public String toString() {

        StringBuilder strBoard = new StringBuilder();

        strBoard.append("\n");

        for (Tile[] tiles : boardArr) {
            for (int j = 0; j < boardArr.length; j++) {
                if (tiles[j] != null) {
                    strBoard.append(tiles[j].getType());
                } else {
                    strBoard.append("0");
                }
                strBoard.append(" | ");
            }
            strBoard.append("\n");
        }

        return strBoard.toString();
    }

    public Tile[][] getBoardArr() {
        return this.boardArr;
    }
}
