package com.uucoding.lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的案例
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/30  21:58
 */
public class ReentrantReadWriteLockExample {

    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //获取ReentrantReadWriteLock对应的读写锁
    static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    /**
     * 读操作
     */
    public static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获取读锁， 开始读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放读锁");
            readLock.unlock();
        }
    }
    /**
     * 写操作
     */
    public static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获取写锁， 开始写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
            writeLock.unlock();
        }
    }

    /**
     * 读锁可以多线程持有，但是写锁只能一个线程持有
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> read(), "thread 01").start();
        new Thread(() -> read(), "thread 02").start();
        new Thread(() -> write(), "thread 03").start();
        new Thread(() -> write(), "thread 04").start();
    }
}
