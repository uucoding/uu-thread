package com.uucoding.cas;

/**
 * 模拟CAS，等价代码
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/1  20:12
 */
public class SimulateCAS {

    // V
    private volatile int value;

    /**
     * 模拟CAS比较交换
     * @param expectedValue 期望值
     * @param newValue  新的值
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if(oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }
}
