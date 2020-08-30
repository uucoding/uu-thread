package com.uucoding.lock.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 尝试获取锁，如果不成功，那么便释放锁
 * <p>
 * {@link Lock#tryLock()}
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/27  19:03
 */
public class TryLockExample implements Runnable {
    int flag = 0;
    static Lock lockA = new ReentrantLock();
    static Lock lockB = new ReentrantLock();

    public static void main(String[] args) {
        TryLockExample tryLockExampleA = new TryLockExample();
        tryLockExampleA.flag = 1;
        TryLockExample tryLockExampleB = new TryLockExample();
        tryLockExampleB.flag = 2;

        new Thread(tryLockExampleA).start();
        new Thread(tryLockExampleB).start();

    }

    @Override
    public void run() {
        while (true) {
            if (flag == 1) {
                try {
                    // 尝试
                    if (lockA.tryLock(800, TimeUnit.MILLISECONDS)) {
                        System.out.println("线程1成功获取lockA");
                        Thread.sleep(new Random().nextInt(1000));
                        try {
                            if (lockB.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("线程1成功获取lockB");
                                    System.out.println("线程1成功获取lockA、lockB");
                                    break;
                                } finally {
                                    lockB.unlock();
                                }
                            } else {
                                System.out.println("线程1获取lockB失败");
                            }
                        } finally {
                            // lock锁需要手动释放
                            lockA.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("线程1获取lockA失败");
                    }
                } catch (InterruptedException e) {
                    System.out.println("线程1异常中断");
                }
            }

            if (flag == 2) {
                try {
                    if (lockB.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        System.out.println("线程2成功获取lockB");
                        Thread.sleep(new Random().nextInt(1000));
                        try {
                            if (lockA.tryLock(3000, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("线程2成功获取lockA");
                                    System.out.println("线程2成功获取lockA、lockB");
                                    break;
                                } finally {
                                    lockA.unlock();
                                }
                            } else {
                                System.out.println("线程2获取lockA失败");
                            }
                        } finally {
                            // lock锁需要手动释放
                            lockB.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("线程2获取lockB失败");
                    }
                } catch (InterruptedException e) {
                    System.out.println("线程2异常中断");
                }
            }
        }

    }
}
