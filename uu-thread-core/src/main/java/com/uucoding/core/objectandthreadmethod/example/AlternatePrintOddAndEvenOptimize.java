package com.uucoding.core.objectandthreadmethod.example;

/**
 * 交替打印奇偶数字优化方案: 使用wait 和 notify
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/25  22:39
 */
public class AlternatePrintOddAndEvenOptimize implements Runnable{

    private static Object locked = new Object();

    private static int count = 0;

    @Override
    public void run() {
        while (count <= 100) {
            synchronized (locked) {
                System.out.println(Thread.currentThread().getName() + ":" + count++);
                try {
                    //打印后就通知另一个线程打印
                    locked.notify();
                    // 如果任务还没有结束，那就释放掉锁
                    if (count <= 100) {
                        locked.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        AlternatePrintOddAndEvenOptimize alternatePrintOddAndEvenOptimize = new AlternatePrintOddAndEvenOptimize();
        new Thread(alternatePrintOddAndEvenOptimize, "偶数线程").start();
        // 防止奇偶错乱，休眠10ms
        Thread.sleep(10);
        new Thread(alternatePrintOddAndEvenOptimize, "奇数线程").start();

    }
}
