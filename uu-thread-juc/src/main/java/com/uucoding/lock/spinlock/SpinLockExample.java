package com.uucoding.lock.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁演示
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/31  01:11
 */
public class SpinLockExample {

    static AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 加锁
     */
    public void lock() {
        Thread currentThread = Thread.currentThread();
        // 将当前线程上锁，如果不能上锁，那就一直循环（自旋）
        while (!atomicReference.compareAndSet(null, currentThread)) {
            System.out.println(currentThread.getName() + "获取锁失败，尝试重新获取！");
        }
    }

    /**
     * 解锁
     */
    public void unlock() {
        Thread currentThread = Thread.currentThread();
        // 把当前线程解锁
        atomicReference.compareAndSet(currentThread, null);
    }

    public static void main(String[] args) {
        SpinLockExample spinLockExample = new SpinLockExample();

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "尝试获取锁。。");
            spinLockExample.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "获取锁成功");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                spinLockExample.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
