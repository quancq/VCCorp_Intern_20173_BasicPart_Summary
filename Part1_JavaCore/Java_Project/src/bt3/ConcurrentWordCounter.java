/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author quancq
 */
public class ConcurrentWordCounter {

    private AtomicInteger count;
    private String word;

    public ConcurrentWordCounter(String word) {
        this.word = word;
        this.count = new AtomicInteger(1);
    }
    
    public String getWord() {
        return word;
    }

    public int getCount() {
        return count.get();
    }

    public int increment() {
        return count.incrementAndGet();
    }
}
