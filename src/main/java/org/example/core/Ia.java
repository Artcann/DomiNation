package org.example.core;
import java.io.IOException;
import java.util.*;

public class Ia extends Player{

    private ArrayList<Integer[][]> allMove = new ArrayList<Integer[][]>();
    private ArrayList<Integer[][]> allLegalMove = new ArrayList<>();
    private Integer[][] move;
    private Domino[] bestDomino;
    private Board board = new Board();
    private GameEngine jeu = new GameEngine();

    public Ia(Castle castle, Board board) throws IOException {
        super(castle, board);
        allMove();
    }

    private void allMove(){
        for(int a = 0; a < 9; a++){
            for(int b = 0; b < 9; b++){
                for(int c = 0; c < 9; c++){
                    for(int d = 0; d < 9; d++){
                        Integer[][] move = {{a, b}, {c, d}};
                        allMove.add(move);
                    }
                }
            }
        }
    }

    private ArrayList<Integer[][]> allLegalsMovesForAGivenDomino(Domino[] domino){
        for(Integer[][] move: allMove){
            if(this.board.isValidMove(move, domino)){
                allLegalMove.add(move);
            }
        }
        return allLegalMove;
    }

    public int simulateScore(Integer[][] move, Domino[] domino){
        int score;
        Board simulateBoard = this.board;
        simulateBoard.placeDomino(move, domino);
        score = simulateBoard.computeScore();
        return score;
    }

    private Integer[][] bestMove(Domino[] domino){
        TreeMap<Integer, Integer[][]> bestMovePerDomino = new TreeMap();
        allLegalsMovesForAGivenDomino(domino);
        for(Integer[][] move: allLegalMove){
            bestMovePerDomino.put(simulateScore(move, domino), move);
        }
        Integer[][] bestMove = bestMovePerDomino.lastEntry().getValue();
        return bestMove;
    }

    private Domino[] bestDominoToPlay(){
        List<Domino[]> actualTable = jeu.getTable();
        TreeMap<Integer, Domino[]> bestMoveForEachDominoAvailable = new TreeMap();
        for(Domino[] domino : actualTable){
            bestMoveForEachDominoAvailable.put(simulateScore(bestMove(domino), domino), domino);
        }
        Domino[] bestDominoToPlay = bestMoveForEachDominoAvailable.lastEntry().getValue();
        return bestDominoToPlay;
    }

    public void executeBestMove(){
        bestDominoToPlay();
        this.board.placeDomino(bestMove(bestDominoToPlay()) ,bestDominoToPlay());
    }
}
