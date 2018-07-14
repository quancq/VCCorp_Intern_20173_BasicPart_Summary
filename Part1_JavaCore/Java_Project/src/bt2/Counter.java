/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt2;

/**
 * Wrapper class for counting
 *
 * @author quancq
 */
public class Counter {

    private int count;

    public Counter() {
        this.count = 1;
    }

    public Counter(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void increase() {
        ++count;
    }

    public void increase(int num) {
        count += num;
    }

    public void decrease() {
        if (count > 0) {
            --count;
        }
    }
}
