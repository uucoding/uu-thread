package com.uucoding.synchronized_;

/**
 * 演示对象锁：同步代码锁(this锁)
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/22  19:53
 */
public class ObjectLockCodeBlock1 implements Runnable{
    @Override
    public void run() {
        // 获取当前对象的锁
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " 获取到了锁");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 释放锁");
        }
    }

    public static void main(String[] args) {
        ObjectLockCodeBlock1 objectLockCodeBlock1 = new ObjectLockCodeBlock1();

        Thread threadA = new Thread(objectLockCodeBlock1);
        Thread threadB = new Thread(objectLockCodeBlock1);

        threadA.start();
        threadB.start();

        while (threadA.isAlive() || threadB.isAlive()) {}

        System.out.println("所有线程执行完毕");
    }
}
