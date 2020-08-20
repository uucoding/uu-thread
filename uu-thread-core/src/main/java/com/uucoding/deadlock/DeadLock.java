package com.uucoding.deadlock;

/**
 * 简单死锁案例
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/18  18:45
 */
public class DeadLock implements Runnable{

    static Object o1 = new Object();
    static Object o2 = new Object();
    int num = 0;
    @Override
    public void run() {
        if (num == 1) {
            synchronized (o1) {
                System.out.println("线程A 获取o1锁");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("线程A 获取o2锁");
                }
            }
            System.out.println("线程A 执行完毕");
        }

        if (num == 2) {
            synchronized (o2) {
                System.out.println("线程B 获取o2锁");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("线程B 获取o1锁");
                }
            }
            System.out.println("线程B 执行完毕");
        }
    }

    public static void main(String[] args) {
        DeadLock deadLockA = new DeadLock();
        deadLockA.num = 1;
        DeadLock deadLockB = new DeadLock();
        deadLockB.num = 2;
        new Thread(deadLockA).start();
        new Thread(deadLockB).start();
    }
}
