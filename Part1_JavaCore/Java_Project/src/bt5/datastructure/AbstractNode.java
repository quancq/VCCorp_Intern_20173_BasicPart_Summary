/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt5.datastructure;

/**
 * Abstract node that all concrete node must implement
 * @author quancq
 */
public abstract class AbstractNode {
    private int row;
    private int col;

    public AbstractNode(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractNode other = (AbstractNode) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public String toString() {
        return "AbstractNode{" + "row=" + row + ", col=" + col + '}';
    }
    
    public abstract boolean isRegularNode();
}
