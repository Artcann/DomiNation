package org.example.core;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

public class Ia extends Player{

    private final ArrayList<Integer[][]> allMove = new ArrayList<>();
    private Integer[][] move;
    private Domino[] bestDomino;

    public Ia(Castle castle, Board board, GameEngine gameEngine) {
        super(castle, board, gameEngine);

        allMove();
    }

    private void allMove(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                Integer[] move = new Integer[]{i, j};

                // Handle Corners
                if(i == 0 && j == 0) {
                    allMove.add(new Integer[][]{move, {i, j+1}});
                    allMove.add(new Integer[][]{move, {i+1, j}});
                }
                else if(i == 0 && j == 8) {
                    allMove.add(new Integer[][]{move, {i+1, j}});
                    allMove.add(new Integer[][]{move, {i, j-1}});
                }
                else if(i == 8 && j == 0) {
                    allMove.add(new Integer[][]{move, {i-1, j}});
                    allMove.add(new Integer[][]{move, {i, j+1}});
                }
                else if(i == 8 && j == 8) {
                    allMove.add(new Integer[][]{move, {i-1, j}});
                    allMove.add(new Integer[][]{move, {i, j-1}});
                }

                // Handle Borders
                else if(i == 0) {
                    allMove.add(new Integer[][]{move, {i+1, j}});
                    allMove.add(new Integer[][]{move, {i, j-1}});
                    allMove.add(new Integer[][]{move, {i, j+1}});
                }
                else if(i == 8) {
                    allMove.add(new Integer[][]{move, {i-1, j}});
                    allMove.add(new Integer[][]{move, {i, j+1}});
                    allMove.add(new Integer[][]{move, {i, j-1}});
                }
                else if(j == 0) {
                    allMove.add(new Integer[][]{move, {i+1, j}});
                    allMove.add(new Integer[][]{move, {i-1, j}});
                    allMove.add(new Integer[][]{move, {i, j+1}});
                }
                else if(j == 8) {
                    allMove.add(new Integer[][]{move, {i+1, j}});
                    allMove.add(new Integer[][]{move, {i-1, j}});
                    allMove.add(new Integer[][]{move, {i, j-1}});
                }

                //Handle Center
                else {
                    allMove.add(new Integer[][]{move, {i+1, j}});
                    allMove.add(new Integer[][]{move, {i-1, j}});
                    allMove.add(new Integer[][]{move, {i, j+1}});
                    allMove.add(new Integer[][]{move, {i, j-1}});
                }
            }
        }
    }

    public List<Integer[][]> allLegalMove(Domino[] domino){
        List<Integer[][]> allLegalMove = new ArrayList<>();
        for(Integer[][] move: allMove){
            if(this.board.isValidMove(move, domino)){
                allLegalMove.add(move);
            }
        }
        return allLegalMove;
    }

    public int simulateScore(Integer[][] move, Domino[] domino){
        int score;
        Board simulateBoard = null;
        try {
            simulateBoard = new Board();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert simulateBoard != null;
        Tile[][] boardCopy = new Tile[9][9];
        for(int i = 0; i<9; i++) {
            boardCopy[i] = Arrays.copyOf(this.board.getBoardArr()[i], 9);
        }

        simulateBoard.initBoard(boardCopy);
        simulateBoard.placeDomino(move, domino);
        score = simulateBoard.computeScore();
        return score;
    }

    public Integer[][] bestMove(Domino[] domino){
        TreeMap<Integer, Integer[][]> bestMovePerDomino = new TreeMap<>();
        List<Integer[][]> allLegalMove = allLegalMove(domino);
        for(Integer[][] move: allLegalMove){
            bestMovePerDomino.put(simulateScore(move, domino), move);
        }
        if (bestMovePerDomino.lastEntry() != null) {
            return bestMovePerDomino.lastEntry().getValue();
        } else {
            return null;
        }

    }

    private Domino[] bestDominoToPlay(){
        List<Domino[]> actualTable = this.gameEngine.getTable();
        TreeMap<Integer, Domino[]> bestMoveForEachDominoAvailable = new TreeMap<>();
        for(Domino[] domino : actualTable){
            if(bestMove(domino) != null) {
                bestMoveForEachDominoAvailable.put(simulateScore(bestMove(domino), domino), domino);
            }
        }
        return bestMoveForEachDominoAvailable.lastEntry().getValue();
    }

    public Domino[] executeBestMove(){
        Domino[] domino = bestDominoToPlay();
        this.board.placeDomino(bestMove(domino), domino);
        return domino;
    }

    public ArrayList<Integer[][]> getAllMove() {
        return allMove;
    }
}
