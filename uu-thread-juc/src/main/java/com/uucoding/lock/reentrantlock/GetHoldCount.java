package com.uucoding.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示可重入锁，获取锁的次数{@link ReentrantLock#getHoldCount()}
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/29  19:05
 */
public class GetHoldCount {

    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        printCount();
    }

    private static void printCount() {
        // 加锁
        reentrantLock.lock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.lock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.lock();
        System.out.println(reentrantLock.getHoldCount());
        // 解锁
        reentrantLock.unlock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.unlock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.unlock();
        System.out.println(reentrantLock.getHoldCount());

    }
}
