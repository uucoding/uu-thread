package com.uucoding.cas;

/**
 * 模拟CAS，等价代码
 * 使用两个线程竞争，并debugger查看线程运行过程
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/1  20:12
 */
public class TwoThreadCompetition implements Runnable {

    // V
    private volatile int value;

    /**
     * 模拟CAS比较交换
     *
     * @param expectedValue 期望值
     * @param newValue      新的值
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        // 此处打断点查看值的变化
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    @Override
    public void run() {
        compareAndSwap(0, 1);
    }

    public static void main(String[] args) throws InterruptedException {
        TwoThreadCompetition twoThreadCompetition = new TwoThreadCompetition();
        twoThreadCompetition.value = 0;
        Thread threadA = new Thread(twoThreadCompetition);
        Thread threadB = new Thread(twoThreadCompetition);
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        System.out.println(twoThreadCompetition.value);
    }
}
