package com.uucoding.synchronized_;

/**
 * 演示对象锁：修饰方法的锁（方法锁）
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/22  19:53
 */
public class ObjectLockMethod implements Runnable{
    @Override
    public void run() {
        operate();
    }

    /**
     * 同步方法锁
     */
    public synchronized void operate() {
        System.out.println(Thread.currentThread().getName() + " 获取到了锁");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 释放锁");
    }

    public static void main(String[] args) {
        ObjectLockMethod objectLockMethod = new ObjectLockMethod();

        Thread threadA = new Thread(objectLockMethod);
        Thread threadB = new Thread(objectLockMethod);

        threadA.start();
        threadB.start();

        while (threadA.isAlive() || threadB.isAlive()) {}

        System.out.println("所有线程执行完毕");
    }
}
