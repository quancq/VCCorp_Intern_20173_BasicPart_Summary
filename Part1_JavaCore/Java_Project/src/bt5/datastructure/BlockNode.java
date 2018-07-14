/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt5.datastructure;

/**
 * Represent block in maze
 * @author quancq
 */
public class BlockNode extends AbstractNode{

    public BlockNode(int row, int col) {
        super(row, col);
    }
    
    @Override
    public boolean isRegularNode() {
        return false;
    }
    
}
