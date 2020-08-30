package com.uucoding.lock.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类比{@link Lock#tryLock(long, TimeUnit)}只不过{@link Lock#lockInterruptibly()}是无限等待
 * 在等待锁的过程中，线程可以被中断
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/27  19:53
 */
public class LockInterruptiblyExample implements Runnable{

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        LockInterruptiblyExample lockInterruptiblyExample = new LockInterruptiblyExample();

        Thread threadA = new Thread(lockInterruptiblyExample);
        Thread threadB = new Thread(lockInterruptiblyExample);
        threadA.start();
        threadB.start();

        Thread.sleep(200);
        // 中断B
        threadB.interrupt();
        // 中断A
        threadA.interrupt();
    }

    @Override
    public void run() {
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + " 获取锁");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 睡眠期间的时候被中断");
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " 等待锁的时候被中断");
        }
    }
}
