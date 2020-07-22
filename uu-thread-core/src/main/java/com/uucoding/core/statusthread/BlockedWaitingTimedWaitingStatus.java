package com.uucoding.core.statusthread;

/**
 * 演示：Blocked、Waiting、TIMED_WAITING三种状态
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/22  13:57
 */
public class BlockedWaitingTimedWaitingStatus implements Runnable{

    public static void main(String[] args) throws InterruptedException{
        BlockedWaitingTimedWaitingStatus blockedWaitingTimedWaitingStatus = new BlockedWaitingTimedWaitingStatus();
        Thread thread1 = new Thread(blockedWaitingTimedWaitingStatus);
        thread1.start();
        Thread thread2 = new Thread(blockedWaitingTimedWaitingStatus);
        thread2.start();
        // 休息100ms让线程1 执行到run 里面的sleep方法
        Thread.sleep(100);
        System.out.println(thread1.getState()); // output: TIMED_WAITING
        System.out.println(thread2.getState()); // output: BLOCKED

        // 休息1ms，保证线程1进入run 里面的wait方法
        Thread.sleep(1000);

        System.out.println(thread1.getState()); // output: WAITING
    }

    @Override
    public void run() {
        test();
    }

    /**
     * synchronized 修饰，会让先进来的线程持有锁，后一个线程则进入Blocked状态
     */
    public synchronized void test() {
        try {
            // Thread.sleep(1000) 会让先进来的那个线程 进入Timed Waiting状态
            Thread.sleep(1000);
            // 让线程 进入Waiting状态
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
