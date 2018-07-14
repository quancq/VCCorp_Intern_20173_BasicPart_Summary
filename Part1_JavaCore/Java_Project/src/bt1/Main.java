/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt1;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author quancq
 */
public class Main {

    private static Set<Integer> set1;
    private static Set<Integer> set2;
    private static Set<Integer> intersectionSet;
    private static Set<Integer> unionSet;
    private static Set<Integer> intersectionSet1;
    private static Set<Integer> unionSet1;

    public static void main(String[] args) {
        int numElements = 5000000;
        double minCommonElementRate = 0.02;
        generate(numElements, minCommonElementRate);
        
        System.out.println("Size of set 1 : " + set1.size());
        System.out.println("Size of set 2 : " + set2.size());
        
        System.out.println("===========================");
        long startTime = System.currentTimeMillis();
        findUnionAndIntersectionSet();
        long finishTime = System.currentTimeMillis();
        System.out.println("Size of union set : " + unionSet.size());
        System.out.println("Size of intersection set : " + intersectionSet.size());
        System.out.printf("Time find union and intersection : %d ms\n", ((finishTime - startTime)));
        
        System.out.println("===========================");
        startTime = System.currentTimeMillis();
        findUnionAndIntersectionSet1();
        finishTime = System.currentTimeMillis();
        System.out.println("Size of union set : " + unionSet1.size());
        System.out.println("Size of intersection set : " + intersectionSet1.size());
        System.out.printf("Time find union and intersection : %d ms\n", ((finishTime - startTime)));

    }

    private static void generate(int numElements, double minCommonElementRate) {
        Random R = new Random();

        // Generate random set1
        set1 = new HashSet<>(numElements);
        while (set1.size() < numElements) {
            set1.add(R.nextInt());
        }

        if (minCommonElementRate < 0 || minCommonElementRate > 1.0) {
            minCommonElementRate = 0.05;
        }
        int numCommonElements = (int) Math.ceil(numElements * minCommonElementRate);

        // Generate random set2
        set2 = new HashSet<>(numElements);
        for (Integer i : set1) {
            if (set2.size() >= numCommonElements) {
                break;
            }
            set2.add(i);
        }
        while (set2.size() < numElements) {
            set2.add(R.nextInt());
        }

        System.out.printf("Generate %d integer with at least %d common elements\n",
                numElements, numCommonElements);
    }

    private static void findUnionAndIntersectionSet(){
        unionSet = new HashSet<>(set1);
        intersectionSet = new HashSet<>();
        for(Integer i : set2){
            if(set1.contains(i)){
                intersectionSet.add(i);
            }else{
                unionSet.add(i);
            }
        }
    }
    
    private static void findUnionAndIntersectionSet1(){
        unionSet1 = new HashSet<>(set1);
        unionSet1.addAll(set2);
        intersectionSet1 = new HashSet<>(set1);
        intersectionSet1.retainAll(set2);
    }

}
