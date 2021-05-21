package org.example.core;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class Ia extends Player{


    ArrayList<Integer[][]> allMove = new ArrayList<>();
    ArrayList<Integer[][]> allLegalMove = new ArrayList<>();
    Integer[][] bestMove;
    Domino[] bestDomino;
    Board board = new Board();


    public Ia(Castle castle, Board board) throws IOException {
        super(castle, board);
    }


    private ArrayList<Integer[][]> allMove(Domino[] domino){

                    // => [[[0,1], [0,2]], [[1,1], [1,2]], [[2,1], [1,1]] ... ]

        return allMove;
    }


    private ArrayList<Integer[][]> allLegalMove(Domino[] domino){
        for(Integer[][] move: allMove){
            if(this.board.isValidMove(move, domino)){
                allLegalMove.add(move);
            }
            else {

            }
        }
        return allLegalMove;
    }


    private Integer[][] bestMove(Domino[] domino){
        for(Integer[][] move: allLegalMove){
            board.simuleScore(move, domino);
            // sort allLegalMove by growing simuleScore -> Use a sort algorithm from moodle course
            // bestMove = last element of allLegalMove after sorting
        }
        return bestMove;
    }


    private Domino[] bestDomino(){
        // Select the best domino by comparing best move from each domino
        Domino[] table = getTable();
        for(Domino domino : table){
            //bestDomino = max(bestMove(domino))
        }
        return bestDomino;
    }


    public void executeBestMove(){
        // Select the best domino and play it with the best legal move
        // placeDomino
    }

}
