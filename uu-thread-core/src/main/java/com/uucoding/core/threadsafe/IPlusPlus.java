package com.uucoding.core.threadsafe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * i++问题演示，并记录出现问题的数据
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/28  09:45
 */
public class IPlusPlus implements Runnable {
    int i = 0;
    AtomicInteger realIndex = new AtomicInteger(); // 实际值
    AtomicInteger errorIndex = new AtomicInteger(); // 错误的位置
    boolean[] marked = new boolean[1000000];

    // 表示等待线程，等待几个，等待完才可以执行下一步操作
    CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);
    static Object object = new Object();

    @Override
    public void run() {
        // 特殊标记情况，如果在i = 1的位置发生错误，不将0置为true就无法校验处理
        marked[0] = true;
        for (int index = 0; index < 100000; index++) {
            try {
                // 重置一下，以便下次可以继续使用
                cyclicBarrier2.reset();
                // 等待两个线程都执行完毕，在执行后面的语句
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            i++;
            try {
                // 重置一下，以便下次可以继续使用
                cyclicBarrier1.reset();
                // 等待两个线程都执行完毕，在执行后面的语句,此时两个线程都完成了++操作（单极可能在加的过程中出现线程切换，导致加错）
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.getAndIncrement();
            synchronized (object) {
                // 如果这个下标已经存在marked中，且前一个也为true表示重复执行了，
                //此处正常情况下 i-1的位置应该是false，如果前面是true，那么表示问题i少加了一次
                if (marked[i] && marked[i - 1]) {
                    System.out.println("重复执行的数据为：" + i);
                    errorIndex.getAndIncrement();
                }
                marked[i] = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IPlusPlus iPlusPlus = new IPlusPlus();
        Thread thread1 = new Thread(iPlusPlus);
        Thread thread2 = new Thread(iPlusPlus);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面执行值为："+ iPlusPlus.i);
        System.out.println("实际执行值为："+ iPlusPlus.realIndex);
        System.out.println("重复执行的个数为："+ iPlusPlus.errorIndex);
    }
}
