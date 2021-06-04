package org.example.core;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class Ia extends Player{

    ArrayList<Integer[][]> allMove = new ArrayList<>();
    ArrayList<Integer[][]> allLegalMove = new ArrayList<>();
    Integer[][] move;
    Integer[][] bestMove;
    Domino[] bestDomino;
    Board board = new Board();
    GameEngine jeu = new GameEngine();


    public Ia(Castle castle, Board board) throws IOException {
        super(castle, board);
    }


    private ArrayList<Integer[][]> allMove(Domino[] domino){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){ // ca marche vraiment ce truc ?
                move[i][j] = i;
            }
        }
        return allMove;
    }


    private ArrayList<Integer[][]> allLegalMove(Domino[] domino){
        for(Integer[][] move: allMove){
            if(this.board.isValidMove(move, domino)){
                allLegalMove.add(move);
            }
        }
        return allLegalMove;
    }


    private Integer[][] bestMove(Domino[] domino){
        for(Integer[][] move: allLegalMove){
            int score = board.simuleScore(move, domino);
            // bestMove = last element of allLegalMove after sorting
        }
        return bestMove;
    }


    private Domino[] bestDomino(){
        // Select the best domino by comparing best move from each domino
        List<Domino[]> table = jeu.getTable();
        for(Domino[] domino : table){
        }
        return bestDomino;
    }


    public void executeBestMove(){
        // Select the best domino and play it with the best legal move
        // placeDomino
    }

}
