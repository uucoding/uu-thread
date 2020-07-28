package com.uucoding.core.threadsafe;

/**
 * 死锁问题
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/28  14:32
 */
public class DeadLock implements Runnable{
    static Object lockedA = new Object();
    static Object lockedB = new Object();
    int num = 1;
    public static void main(String[] args) {
        DeadLock deadLockA = new DeadLock();
        deadLockA.num = 1;
        DeadLock deadLockB = new DeadLock();
        deadLockB.num = 0;

        new Thread(deadLockA).start();
        new Thread(deadLockB).start();
    }

    @Override
    public void run() {
        if (num == 1) {
            synchronized (lockedA) {
                System.out.println(Thread.currentThread().getName() + "获取 lockedA");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockedB) {
                    System.out.println(Thread.currentThread().getName() + "获取 lockedB");
                }
            }
        } else {
            synchronized (lockedB) {
                System.out.println(Thread.currentThread().getName() + "获取 lockedB");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockedA) {
                    System.out.println(Thread.currentThread().getName() + "获取 lockedA");
                }
            }
        }
    }
}
