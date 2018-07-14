/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt4;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import static java.lang.Math.*;
import java.util.Random;

/**
 *
 * @author quancq
 */
public class Main {

    public static void main(String[] args) {
        // Generate random set1 of points
        int numPoints1 = 8000;
        Point focus1 = new Point(800, 800);
        int radius1 = 400;
        Set<Point> set1 = generatePoints(numPoints1, focus1, radius1);
        System.out.println("Set 1 : " + set1.size() + " points");
//        printSet(set1);

        // Generate random set2 of points
        int numPoints2 = 10000;
        Point focus2 = new Point(4000, 800);
        int radius2 = 500;
        Set<Point> set2 = generatePoints(numPoints2, focus2, radius2);
        System.out.println("Set 2 : " + set2.size() + " points");
//        printSet(set2);

        // Generate random set3 of points
        int numPoints3 = 12000;
        Point focus3 = new Point(2400, 2400);
        int radius3 = 600;
        Set<Point> set3 = generatePoints(numPoints3, focus3, radius3);
        System.out.println("Set 3 : " + set3.size() + " points");
//        printSet(set3);

        Set<Point> points = new HashSet<>(set1);
        points.addAll(set2);
        points.addAll(set3);

        System.out.println("Total points : " + points.size() + " points");
        String outputPath = "./src/bt4/output4.txt";
        writeFile(outputPath, points);
    }

    private static Set<Point> generatePoints(int numPoints, Point focus, int radius) {
        HashSet<Point> points = new HashSet<>();
        while (points.size() < numPoints) {
            int x = randomInt(focus.getX() - radius, focus.getX() + radius);
            double temp = pow(radius, 2) - pow(focus.getX() - x, 2);
            if (temp < 0) {
                continue;
            }
            temp = sqrt(temp);
            int y = randomInt(focus.getY() - temp, focus.getY() + temp);
            Point p = new Point(x, y);
            if (!points.contains(p)) {
                points.add(p);
            }
        }

        return points;
    }

    private static int randomInt(double min, double max) {
        int minInt = (int) (ceil(min));
        int maxInt = (int) (floor(max));
        Random R = new Random();
        return R.nextInt(maxInt - minInt + 1) + minInt;
    }

    private static void printSet(Set<Point> points) {
        for (Point p : points) {
            System.out.println(p);
        }
    }

    private static void writeFile(String path, Set<Point> points) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path))
        )) {

            for (Point p : points) {
                bw.write(p.getX() + "," + p.getY() + "\n");
            }
            System.out.println("Write file to " + path + " done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
