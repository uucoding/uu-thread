package com.uucoding.deadlock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用tryLock避免死锁
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/20  19:41
 */
public class TryLockAvoidDeadLock implements Runnable {
    int flag = 0;

    static Lock lockA = new ReentrantLock();
    static Lock lockB = new ReentrantLock();

    public static void main(String[] args) {
        TryLockAvoidDeadLock A = new TryLockAvoidDeadLock();
        A.flag = 1;
        TryLockAvoidDeadLock B = new TryLockAvoidDeadLock();
        B.flag = 2;
        new Thread(A, "线程A").start();
        new Thread(B, "线程B").start();
    }

    @Override
    public void run() {
        while (true) {
            if (flag == 1) {
                try {
                    // 线程A 尝试获取锁A
                    System.out.println(Thread.currentThread().getName() + " 尝试获取 lockA");
                    if (lockA.tryLock(800, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + " 成功获取 lockA");
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                        System.out.println(Thread.currentThread().getName() + " 尝试获取 lockB");
                        if (lockB.tryLock(800, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() + " 成功获取 lockB");

                            TimeUnit.SECONDS.sleep(1);
                            System.out.println(Thread.currentThread().getName() + " 释放 lockB");

                            lockB.unlock();
                            System.out.println(Thread.currentThread().getName() + " 释放 lockA");
                            lockA.unlock();
                            break;
                        } else {
                            System.out.println(Thread.currentThread().getName() + " 获取 lockB 失败");
                            lockA.unlock();
                            System.out.println(Thread.currentThread().getName() + " 释放 lockA");
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取 lockA 失败");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (flag == 2) {
                try {
                    // 线程A 尝试获取锁A
                    System.out.println(Thread.currentThread().getName() + "尝试获取 lockB");
                    if (lockB.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + "成功获取 lockB");
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                        System.out.println(Thread.currentThread().getName() + "尝试获取 lockA");
                        if (lockA.tryLock(3000, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() + "成功获取 lockA");
                            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));

                            System.out.println(Thread.currentThread().getName() + "释放 lockA");
                            lockA.unlock();

                            System.out.println(Thread.currentThread().getName() + "释放 lockB");
                            lockB.unlock();

                            break;
                        } else {
                            System.out.println(Thread.currentThread().getName() + "获取 lockA 失败");
                            lockB.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放 lockB");
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取 lockB 失败");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
