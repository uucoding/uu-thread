package com.uucoding.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示可重入获取锁 递归方式
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/29  19:10
 */
public class RecursionExample {

    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        accessResource();
    }

    public static void accessResource() {
        reentrantLock.lock();
        try {
            System.out.println("处理资源");
            if (reentrantLock.getHoldCount() < 5) {
                System.out.println(reentrantLock.getHoldCount());
                accessResource();
            }
        } finally {
            reentrantLock.unlock();
            System.out.println(reentrantLock.getHoldCount());
        }
    }
}
