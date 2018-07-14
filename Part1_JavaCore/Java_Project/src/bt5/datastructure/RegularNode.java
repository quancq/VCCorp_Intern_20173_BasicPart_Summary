/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt5.datastructure;

/**
 * Represent possible move node in maze
 * @author quancq
 */
public class RegularNode extends AbstractNode {

    public static final String VISITED_STATE = "Visited";
    public static final String CANDIDATE_STATE = "Candidate";
    public static final String NOT_TRAVERSE_STATE = "Not Traverse";

    private RegularNode parentNode;

    private int currCost;
    private int estimateFutureCost;
    private long totalCost;
    private String state;

    public RegularNode(int row, int col) {
        super(row, col);

        // Init cost
        currCost = Integer.MAX_VALUE;
        estimateFutureCost = Integer.MAX_VALUE;
        calcTotalCost();

        setState(NOT_TRAVERSE_STATE);
    }

    @Override
    public boolean isRegularNode() {
        return true;
    }

    public AbstractNode getParentNode() {
        return parentNode;
    }

    public int getCurrCost() {
        return currCost;
    }

    public int getEstimateFutureCost() {
        return estimateFutureCost;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setParentNode(RegularNode parentNode) {
        this.parentNode = parentNode;
    }

    public void setCurrCost(int currCost) {
        this.currCost = currCost;
        calcTotalCost();
    }

    public void setEstimateFutureCost(int estimateFutureCost) {
        this.estimateFutureCost = estimateFutureCost;
        calcTotalCost();
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isState(String state) {
        return this.state.equals(state);
    }

    public long calcTotalCost() {
        totalCost = (long) currCost + (long) estimateFutureCost;
        return totalCost;
    }

    @Override
    public String toString() {
        return "(" + getRow() + ',' + getCol() + " - " 
                + currCost + "," + estimateFutureCost + "," + totalCost + ")";
    }

}
