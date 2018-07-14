/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt7;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quancq
 */
public class Main {
    public static void main(String[] args) {
        
        // Add 9 instances just create into common array list
        ArrayList<VanTai> vanTaiList = new ArrayList<>();
        
        vanTaiList.add(new OTo());
        vanTaiList.add(new OTo());
        vanTaiList.add(new OTo());
        
        vanTaiList.add(new XeDap());
        vanTaiList.add(new XeDap());
        vanTaiList.add(new XeDap());
        
        vanTaiList.add(new XeTai());
        vanTaiList.add(new XeTai());
        vanTaiList.add(new XeTai());
        
        // Invoke diChuyen method with each object in array list
        for(VanTai vt : vanTaiList){
            vt.diChuyen();
        }
        
    }
}
