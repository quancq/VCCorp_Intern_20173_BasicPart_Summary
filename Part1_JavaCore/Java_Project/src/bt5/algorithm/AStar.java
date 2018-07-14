/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt5.algorithm;

import bt5.datastructure.AbstractNode;
import bt5.datastructure.BlockNode;
import bt5.datastructure.RegularNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implement AStar algorithm
 *
 * @author quancq
 */
public class AStar {

    // Codes of maze matrix
    public static final int REGULAR_NODE_CODE = 0;
    public static final int BLOCK_NODE_CODE = 1;
    public static final int FINAL_NODE_CODE = 2;
    public static final int PATH_NODE_CODE = 3;
    public static final int EXPAND_NODE_CODE = -1;

    private int[][] maze;

    // Queue contain candidate moves
    // Head of queue is node have totalCost min
    private PriorityQueue<RegularNode> candidateNodeQueue;
    private ArrayList<RegularNode> visitedNodes;
    private int numExpandNodes;

    private AbstractNode[][] searchArea;
    private int numRows;
    private int numCols;
    private RegularNode initialNode;
    private RegularNode finalNode;

    public AStar(int[][] maze, int initialRow, int initialCol) {
        this.maze = maze;
        numRows = maze.length;
        numCols = maze[0].length;
        numExpandNodes = 0;

        // Init search area and initial node, final node
        initSearchArea(maze, initialRow, initialCol);

        // Init candidate node queue
        candidateNodeQueue = new PriorityQueue<>(new Comparator<RegularNode>() {
            @Override
            public int compare(RegularNode o1, RegularNode o2) {
                long cost1 = o1.getTotalCost();
                long cost2 = o2.getTotalCost();
                if (cost1 < cost2) {
                    return -1;
                } else if (cost1 > cost2) {
                    return 1;
                } else {
                    return o1.getEstimateFutureCost()- o2.getEstimateFutureCost();
                }
            }
        });
        candidateNodeQueue.add(initialNode);

        visitedNodes = new ArrayList<>();

        System.out.println("AStar::Constructor done");
    }

    /**
     * For each iterator, visit node have total cost min. Add successors of
     * current node into queue with cost calculated base on current node's cost.
     * Total cost of each node is equal cost from initial node to current node
     * plus heuristic cost (estimate cost from current node to final node)
     *
     * @return Array List contain shortest path from initial node to final node.
     * If not exist legal path then return empty list
     */
    public ArrayList<RegularNode> findPath() {
        System.out.println("AStar::findPath start");
        System.out.println("Initial node : " + initialNode);
        System.out.println("Final node : " + finalNode);
        ArrayList<RegularNode> path = new ArrayList<>();
        int numPollNodes = 0;

        // Execute A Star algorithm
        while (!candidateNodeQueue.isEmpty()) {
            System.out.println("\nSize of queue : " + candidateNodeQueue.size());
            System.out.println("Queue : " + candidateNodeQueue);

            // Visit node is head of queue
            RegularNode selectedNode = candidateNodeQueue.poll();
            setState(selectedNode, RegularNode.VISITED_STATE);

            ++numPollNodes;

//            System.out.printf("Node poll from queue : %s, currCost = %d, HeuristicCost = %d,"
//                    + " totalCost = %d\n", selectedNode, selectedNode.getCurrCost(),
//                    selectedNode.getEstimateFutureCost(), selectedNode.getTotalCost());
            if (isFinalNode(selectedNode)) {
                // If find target (final node) then calculate completed path
                path = getPath();
                break;
            }
//            maze[selectedNode.getRow()][selectedNode.getCol()] = VISITED_NODE_CODE;
            addSuccessorNodes(selectedNode);
        }
        System.out.println("Num node poll   : " + numPollNodes);
        System.out.println("Num node expand : " + numExpandNodes);
        System.out.println("AStar::findPath done");

        // If not exist legal path then return empty path
        return path;
    }

    private void setState(RegularNode node, String state) {

        if (RegularNode.CANDIDATE_STATE.equals(state)) {
            numExpandNodes++;
            if (!finalNode.equals(node)) {
                maze[node.getRow()][node.getCol()] = AStar.EXPAND_NODE_CODE;
            }
        }

        node.setState(state);
    }

    /**
     * Find path by traverse parent node from final node
     *
     * @return array list contain completed path from initial node to final node
     */
    private ArrayList<RegularNode> getPath() {
        ArrayList<RegularNode> path = new ArrayList<>();

        RegularNode currNode = finalNode;
//        path.add(currNode);
        while (!initialNode.equals(currNode)) {
            currNode = (RegularNode) currNode.getParentNode();
            path.add(currNode);
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Add each of 4 successor (left, right, up, down) nodes of current node
     * into candidate node queue if successor node is regular node and not
     * visited, not in queue. If a successor node existing in queue or visited
     * then update cost if better
     *
     * @param currNode
     */
    private void addSuccessorNodes(RegularNode currNode) {
        // xét xem có cập nhật lại những node đã thăm ko, nếu có thì phải thực hiện lan truyền currCost
        int currCost = currNode.getCurrCost();
        List<RegularNode> possibleSuccessorNodes = getPossibleSuccessorNodes(currNode);
        for (RegularNode successorNode : possibleSuccessorNodes) {
            if (successorNode.isState(RegularNode.NOT_TRAVERSE_STATE)) {
                successorNode.setParentNode(currNode);
                successorNode.setCurrCost(currCost + 1);
                successorNode.setEstimateFutureCost(calcEstimateFutureCost(successorNode));

                setState(successorNode, RegularNode.CANDIDATE_STATE);
                candidateNodeQueue.add(successorNode);
            } else if (successorNode.isState(RegularNode.CANDIDATE_STATE)
                    || successorNode.isState(RegularNode.VISITED_STATE)) {
                if (currCost + 1 < successorNode.getCurrCost()) {
                    // current node is better parent node than current parent node
                    // update parent node
                    successorNode.setParentNode(currNode);
                    successorNode.setCurrCost(currCost + 1);

                    // Remove and add node again order to update queue after update cost
                    candidateNodeQueue.remove(successorNode);
                    candidateNodeQueue.add(successorNode);

                    if (successorNode.isState(RegularNode.VISITED_STATE)) {
                        setState(successorNode, RegularNode.CANDIDATE_STATE);
                    }
                }

//            } else if (successorNode.isState(RegularNode.VISITED_STATE)) {
//                System.out.println("AStar::addSuccessorNodes Not handle visited state");
            }

        }
    }

    /**
     * Init searchArea and initial node, final node
     *
     * @param maze is matrix encode maze
     */
    private void initSearchArea(int[][] maze, int initialRow, int initialCol) {
        searchArea = new AbstractNode[numRows][numCols];
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                switch (maze[row][col]) {
                    case REGULAR_NODE_CODE: {
                        searchArea[row][col] = new RegularNode(row, col);
                        if (row == initialRow && col == initialCol) {
                            initialNode = (RegularNode) searchArea[row][col];
                        }
                        break;
                    }
                    case BLOCK_NODE_CODE: {
                        searchArea[row][col] = new BlockNode(row, col);
                        break;
                    }
                    case FINAL_NODE_CODE: {
                        finalNode = new RegularNode(row, col);
                        searchArea[row][col] = finalNode;
                        break;
                    }
                    default:
                        System.out.printf("Not valid : maze[%d][%d] = %d\n", row, col, maze[row][col]);
                }
            }
        }
        initialNode.setCurrCost(0);
        initialNode.setEstimateFutureCost(calcEstimateFutureCost(initialNode));

        System.out.println("AStar::initSearchArea done");
    }

    /**
     * This is heuristic function in AStar algorithm. Calculate Manhattan
     * distance from current node to final node
     *
     * @param currNode
     * @return heuristic cost of current node
     */
    private int calcEstimateFutureCost(RegularNode currNode) {
        if (finalNode == null) {
            System.out.println("Not init final node");
            return Integer.MAX_VALUE;
        }
        int cost = Math.abs(finalNode.getRow() - currNode.getRow())
                + Math.abs(finalNode.getCol() - currNode.getCol());

        // Test Euclidean distance cost
//        int cost = (int)(Math.sqrt((Math.pow(finalNode.getRow() - currNode.getRow(), 2)
//                + Math.pow(finalNode.getCol() - currNode.getCol(), 2))));
        return (int)(cost * 120);

//        return 
    }

    private boolean isFinalNode(RegularNode currNode) {
        return currNode.equals(finalNode);
    }

    private boolean isRegularNode(int row, int col) {
        return searchArea[row][col].isRegularNode();
    }

    /**
     * Find all legal adjacent of current node in 4 adjacent node (up, down,
     * left, right)
     *
     * @param currNode
     * @return Array list contain all legal adjacent of current node
     */
    private List<RegularNode> getPossibleSuccessorNodes(RegularNode currNode) {
        List<RegularNode> possibleSuccessorNodes = new ArrayList<>();
        int row = currNode.getRow();
        int col = currNode.getCol();

        // Check up neighbor
        if (row > 0 && isRegularNode(row - 1, col)) {
            possibleSuccessorNodes.add((RegularNode) searchArea[row - 1][col]);
        }

        // Check down neighbor
        if (row < numRows - 1 && isRegularNode(row + 1, col)) {
            possibleSuccessorNodes.add((RegularNode) searchArea[row + 1][col]);
        }

        // Check left neighbor
        if (col > 0 && isRegularNode(row, col - 1)) {
            possibleSuccessorNodes.add((RegularNode) searchArea[row][col - 1]);
        }

        // Check right neighbor
        if (row < numCols - 1 && isRegularNode(row, col + 1)) {
            possibleSuccessorNodes.add((RegularNode) searchArea[row][col + 1]);
        }

        return possibleSuccessorNodes;
    }
}
