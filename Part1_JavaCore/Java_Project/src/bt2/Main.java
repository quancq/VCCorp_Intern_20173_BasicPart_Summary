/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt2;

import bt4.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quancq
 */
public class Main {

    public static void main(String[] args) {
        String inputPath = "./src/bt2/01.txt";
        HashMap<String, Counter> mapWordToCount = readFile(inputPath);
        System.out.println("Size of map : " + mapWordToCount.size());
        String outputPath = "./src/bt2/output.txt";
        writeFile(outputPath, mapWordToCount);
    }

    private static HashMap<String, Counter> readFile(String path) {
        HashMap<String, Counter> mapWordToCount = new HashMap<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path))
        )) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().split(" ");
                for (String word : words) {
                    // Increase count of word in map
                    if (word.length() > 0) {
                        if (mapWordToCount.get(word) == null) {
                            mapWordToCount.put(word, new Counter());
                        } else {
                            Counter counter = mapWordToCount.get(word);
                            counter.increase();
                        }
                    }
                }
            }
            System.out.printf("Read file %s and build map to count word done\n", path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return mapWordToCount;
    }

    private static void writeFile(String path, Map<String, Counter> mapWordToCount) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path))
        )) {

            mapWordToCount.forEach((word, count) -> {
                try {
                    bw.write(word + " " + count.getCount() + "\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            System.out.println("Write file to " + path + " done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
