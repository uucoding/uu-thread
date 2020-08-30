package com.uucoding.lock.reentrantlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平和非公平锁的演示
 * <p>
 * {@link java.util.concurrent.locks.ReentrantLock#ReentrantLock(boolean)}
 * ReentrantLock默认是非公平的
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/29  20:05
 */
public class FairOrNonFairLock {
    
    public static void main(String[] args) throws InterruptedException {
        PrintJob printJob = new PrintJob();
        Thread[] threads = new Thread[3];
        Task task = new Task(printJob);
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread(task);
        }

        // 每个线程延迟启动，让其排队进入阻塞队列
        for (int i = 0; i < 3; i++) {
            threads[i].start();
            Thread.sleep(100);
        }
    }

}

class Task implements Runnable {

    private PrintJob printJob;

    public Task(PrintJob printJob) {
        this.printJob = printJob;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 开始执行打印任务");
        printJob.print();
        System.out.println(Thread.currentThread().getName() + " 打印结束");
    }
}

/**
 * 打印任务
 */
class PrintJob {
    // 通过调用 ReentrantLock(boolean)设置公平或非公平锁
//    static Lock lock = new ReentrantLock(true);
    static Lock lock = new ReentrantLock(false);

    /**
     * 调用的时候内部打印两次，根据获取设置锁的额公平与不公平，进行观察打印的额顺序
     */
    public void print() {
        lock.lock();
        try {
            // 随机打印时间
            int seconds = new Random().nextInt(5) + 1;
            try {
                Thread.sleep(seconds * 1000);
                System.out.println(Thread.currentThread().getName() + "打印第一部分耗时 " + seconds + " 秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {
            lock.unlock();
        }

        // 第二次打印：如果是公平锁，那么先执行的线程会加入阻塞队列一起等待
        // 如果是非公平锁，那么会直接执行（因为唤醒其他线程需要时间）
        lock.lock();
        try {
            int seconds = new Random().nextInt(5) + 1;
            System.out.println(Thread.currentThread().getName() + "打印第二部分耗时 " + seconds + " 秒");
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {
            lock.unlock();
        }
    }
}
