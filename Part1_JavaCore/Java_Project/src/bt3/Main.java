/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author quancq
 */
public class Main {

    private static ConcurrentHashMap<String, ConcurrentWordCounter> mapWordToCount = new ConcurrentHashMap<>();
    private static final int NUM_THREADS = 2;

    private static final Comparator<ConcurrentWordCounter> ASCENDING_WORD_COMPARATOR = new Comparator<ConcurrentWordCounter>() {
        @Override
        public int compare(ConcurrentWordCounter o1, ConcurrentWordCounter o2) {
            return o1.getWord().compareTo(o2.getWord());
        }
    };
    private static final Comparator<ConcurrentWordCounter> DESCENDING_COUNT_COMPARATOR = new Comparator<ConcurrentWordCounter>() {
        @Override
        public int compare(ConcurrentWordCounter o1, ConcurrentWordCounter o2) {
            return o2.getCount() - o1.getCount();
        }
    };

    private static final Comparator<ConcurrentWordCounter> ASCENDING_COUNT_COMPARATOR = new Comparator<ConcurrentWordCounter>() {
        @Override
        public int compare(ConcurrentWordCounter o1, ConcurrentWordCounter o2) {
            return o1.getCount() - o2.getCount();
        }
    };

    /**
     * Represent task that process file to count words
     */
    static class MyTask implements Runnable {

        private String filePath;
        private int fileIndex;

        public MyTask(String filePath, int fileIndex) {
            this.filePath = filePath;
            this.fileIndex = fileIndex;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filePath))
            )) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] words = line.trim().split(" ");
                    for (String word : words) {
                        // Increase count of word in map
                        if (word.length() > 0) {
                            if (mapWordToCount.get(word) == null) {
                                mapWordToCount.put(word, new ConcurrentWordCounter(word));
                            } else {
                                ConcurrentWordCounter counter = mapWordToCount.get(word);
                                counter.increment();
                            }
                        }
                    }
                }
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                System.out.printf("Thread %s : processed file %d, %s done\n",
                        Thread.currentThread().getName(), fileIndex, fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        long startTime, finishTime, tempTime;
        startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        String dirPath = "./src/bt3/input 3";
//        String dirPath = "./src/bt3/other_input";
        ArrayList<String> filePaths = getFiles(dirPath);

        for (int i = 0; i < filePaths.size(); ++i) {
            String filePath = filePaths.get(i);
            Runnable task = new MyTask(filePath, i + 1);
            executor.submit(task);
        }

        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }

        System.out.println("Global map build done");
        System.out.println("Size of map : " + mapWordToCount.size());
        tempTime = System.currentTimeMillis();
        System.out.println("Build map time : " + (tempTime - startTime) + " ms");

        String outputPath = "./src/bt3/output3_asc_word.txt";
//        writeFile(outputPath, mapWordToCount);

//        ArrayList<ConcurrentWordCounter> wordCounters = new ArrayList<>(mapWordToCount.values());
//        // Sort words by ascending order
//        Collections.sort(wordCounters, ASCENDING_WORD_COMPARATOR);
//        writeFile(outputPath, wordCounters);
//        
//        // Sort words by descending count
//        Collections.sort(wordCounters, DESCENDING_COUNT_COMPARATOR);
//        outputPath = "./src/bt3/output3_des_count.txt";
//        writeFile(outputPath, wordCounters);
        Collection<ConcurrentWordCounter> wordCounters = mapWordToCount.values();

        // Find top popular word in queue
        int numTopPopularWord = 10;
        ArrayList<ConcurrentWordCounter> popularWords = getTopWordCounters(
                wordCounters, numTopPopularWord, true);
        outputPath = "./src/bt3/output3_top" + numTopPopularWord + "_popular_word.txt";
//        outputPath = "./src/bt3/other_output/output3_top" + numTopPopularWord + "_popular_word.txt";
        writeFile(outputPath, popularWords);

        // Find top unpopular word in queue
        int numTopUnPopularWord = 10;
        ArrayList<ConcurrentWordCounter> unPopularWords = getTopWordCounters(
                wordCounters, numTopPopularWord, false);
        outputPath = "./src/bt3/output3_top" + numTopUnPopularWord + "_unpopular_word.txt";
//        outputPath = "./src/bt3/other_output/output3_top" + numTopUnPopularWord + "_unpopular_word.txt";
        writeFile(outputPath, unPopularWords);

        finishTime = System.currentTimeMillis();
        System.out.println("Find top words time : " + (finishTime - tempTime) + " ms");
        System.out.println("Total time : " + (finishTime - startTime) + " ms");

    }

    /**
     * This method use heap structure. Complexity is O(n + klogn) - k is
     * numTopWords
     *
     * @param wordCounters is Collection contain ConcurrentWordCounter objects
     * @param numTopWords is number word return
     * @param isPopular is true then return top popular word, otherwise return
     * top unpopular word
     * @return ArrayList contain top words
     */
    private static ArrayList<ConcurrentWordCounter> getTopWordCounters(Collection<ConcurrentWordCounter> wordCounters, int numTopWords, boolean isPopular) {
        ArrayList<ConcurrentWordCounter> topWords = new ArrayList<>(numTopWords);
        Comparator comparator = isPopular ? DESCENDING_COUNT_COMPARATOR : ASCENDING_COUNT_COMPARATOR;
        PriorityQueue<ConcurrentWordCounter> wordQueue = new PriorityQueue<>(comparator);
        wordQueue.addAll(wordCounters);
        for (int i = 0; i < numTopWords; ++i) {
            topWords.add(wordQueue.poll());
        }
        return topWords;
    }

    /**
     * This method use sort. Complexity is O(nlogn + k). This method not used
     * because its complexity worse than complexity of method use heap structure
     *
     * @param wordCounters is Collection contain ConcurrentWordCounter objects
     * @param numTopWords is number word return
     * @param isPopular is true then return top popular word, otherwise return
     * top unpopular word
     * @return ArrayList contain top word
     */
    private static ArrayList<ConcurrentWordCounter> getTopWordCounters1(Collection<ConcurrentWordCounter> wordCounters, int numTopWords, boolean isPopular) {
        ArrayList<ConcurrentWordCounter> topWords = new ArrayList<>(numTopWords);
        ArrayList<ConcurrentWordCounter> words = new ArrayList<>(wordCounters);
        Comparator comparator = isPopular ? DESCENDING_COUNT_COMPARATOR : ASCENDING_COUNT_COMPARATOR;
        Collections.sort(words, comparator);
        for (int i = 0; i < numTopWords; ++i) {
            topWords.add(words.get(i));
        }
        return topWords;
    }

    /**
     *
     * @param dir
     * @return all file path of directory directly
     */
    private static ArrayList<String> getFiles(String dirPath) {
        ArrayList<String> filePaths = new ArrayList<>();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file.getAbsolutePath());
            }
        }
        System.out.printf("In directory %s : found %d files\n", dirPath, filePaths.size());
        return filePaths;
    }

    private static void writeFile(String path, Map<String, ConcurrentWordCounter> mapWordToCount) {
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

    private static void writeFile(String path, List<ConcurrentWordCounter> wordCounters) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path))
        )) {

            for (ConcurrentWordCounter wordCounter : wordCounters) {
                bw.write(wordCounter.getWord() + " " + wordCounter.getCount() + "\n");
            }
            System.out.println("Write file to " + path + " done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
