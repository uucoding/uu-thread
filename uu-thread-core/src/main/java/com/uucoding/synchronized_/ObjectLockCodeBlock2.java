package com.uucoding.synchronized_;

/**
 * 演示对象锁：同步代码锁(object锁)
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/22  19:53
 */
public class ObjectLockCodeBlock2 implements Runnable{

    Object lockA = new Object();
    Object lockB = new Object();
    @Override
    public void run() {
        // 获取lockA锁
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " 获取到了lockA锁");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 释放lockA锁");
        }
        // 获取lockB锁, 这里不会干扰其他线程获取lockA，如果这里的锁和上面才用同一个锁处理，就会导致其他线程等待锁
        synchronized (lockB) {
            System.out.println(Thread.currentThread().getName() + " 获取到了lockB锁");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 释放lockB锁");
        }
    }

    public static void main(String[] args) {
        ObjectLockCodeBlock2 objectLockCodeBlock2 = new ObjectLockCodeBlock2();

        Thread threadA = new Thread(objectLockCodeBlock2);
        Thread threadB = new Thread(objectLockCodeBlock2);

        threadA.start();
        threadB.start();

        while (threadA.isAlive() || threadB.isAlive()) {}

        System.out.println("所有线程执行完毕");
    }
}
