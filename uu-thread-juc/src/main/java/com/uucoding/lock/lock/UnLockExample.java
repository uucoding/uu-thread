package com.uucoding.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock锁并不会像synchronized一样，异常时候自动释放锁，所以最好的方式是在finally进行释放，以保证锁能够在异常情况下也可以释放
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/27  20:13
 */
public class UnLockExample implements Runnable {
    static Lock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock();
        try {
            // TODO other
        } finally {
            lock.unlock();
        }
    }
}
