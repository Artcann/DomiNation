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
        this.size = Integer.parseInt(properties.getPropValues("size"));
        this.topBorder = 0;
        this.bottomBorder = size - 1;
        this.leftBorder = 0;
        this.rightBorder = size - 1;
    }


    public void initBoard(Castle castle) {
        this.boardArr = new Tile[(2*this.size)-1][(2*this.size)-1];
        for(int i = 0; i < boardArr.length; i++) {
            for(int j = 0; j < boardArr.length; j++) {
                this.boardArr[i][j] = null;
            }
        }
        this.boardArr[this.size-1][this.size-1] = castle;
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
                domino[1].setPosition(positions[1]);
                updateBoardLimit(positions[0]);
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

        if(!(isDominoInside(move[0]) && isDominoInside(move[1]))) {
            logger.error("The domino is outside the kingdom");
        } else if(!(isTileEmpty(move[0]) && isTileEmpty(move[1]))) {
            logger.error("The tile is not empty");
        } else if(!(hasSameTypeNeighbor(move[0], domino[0]) || hasSameTypeNeighbor(move[1], domino[1]))) {
            logger.error("The domino is not placed near a domino of same type");
        }

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
        if(cell[0] > this.boardArr.length || cell[1] > this.boardArr.length || cell[0] < 0 || cell[1] < 0) {
            return false;
        }

        int mostLeftPosition = cell[1] > this.leftBorder ? cell[1] : this.leftBorder;
        int mostRightPosition = cell[1] < this.rightBorder ? cell[1] : this.rightBorder;
        int highestPosition = cell[0] > this.topBorder ? cell[0] : this.topBorder;
        int lowestPosition = cell[0] < this.bottomBorder ? cell[0] : this.bottomBorder;

        int width = mostRightPosition - mostLeftPosition;
        int heigh = lowestPosition - highestPosition;

        return (width < 5 && heigh < 5);
    }

    private void updateBoardLimit(Integer[] cell) {
        this.leftBorder = cell[1] < this.leftBorder ? cell[1] : this.leftBorder;
        this.rightBorder = cell[1] > this.rightBorder ? cell[1] : this.rightBorder;
        this.topBorder = cell[0] < this.topBorder ? cell[0] : this.topBorder;
        this.bottomBorder = cell[0] > this.bottomBorder ? cell[0] : this.bottomBorder;
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
        if(position[0] == this.topBorder && position[1] == this.leftBorder) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[0] == this.topBorder && position[1] == this.rightBorder) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
        }
        else if(position[0] == this.bottomBorder && position[1] == this.leftBorder) {
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[0] == this.bottomBorder && position[1] == this.rightBorder) {
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
        }

        // Handle Borders
        else if(position[0] == this.topBorder || position[1] == this.topBorder) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[0] == this.bottomBorder || position[1] == this.bottomBorder) {
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
            neighborsList.add(boardArr[position[0]][position[1] - 1]);
        }
        else if(position[0] == this.leftBorder || position[1] == this.leftBorder) {
            neighborsList.add(boardArr[position[0] + 1][position[1]]);
            neighborsList.add(boardArr[position[0] - 1][position[1]]);
            neighborsList.add(boardArr[position[0]][position[1] + 1]);
        }
        else if(position[0] == this.rightBorder || position[1] == this.rightBorder) {
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
