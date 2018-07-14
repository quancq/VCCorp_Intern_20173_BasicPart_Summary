/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt5.algorithm;

import bt5.datastructure.RegularNode;
import java.util.ArrayList;

/**
 * Class manage all algorithms
 * @author quancq
 */
public class AlgorithmManager {
    
    public static final String ASTAR = "AStar";
    
    public static ArrayList<RegularNode> solve(int[][] maze,int initialRow,int initialCol, String algoName){
        switch(algoName){
            case ASTAR: return runAStar(maze, initialRow, initialCol);
            default: return runAStar(maze, initialRow, initialCol);
        }
    }
    
    private static ArrayList<RegularNode> runAStar(int[][] maze, int initialRow, int initialCol){
        AStar aStar = new AStar(maze, initialRow, initialCol);
        ArrayList<RegularNode> path = aStar.findPath();
        return path;
    }
}
