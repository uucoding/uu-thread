package com.uucoding.core.objectandthreadmethod;

/**
 * 演示： wait方法仅仅释放自己的锁，其他线程持有的其他锁不会释放
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/25  19:49
 */
public class WaitOnlyReleaseOwnMonitor {

    private static Object lockA = new Object();
    private static Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "获取 lockA");
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "获取 lockB, 并释放lockA");
                    try {
                        // 校验lockA.wait()是否会释放lockB
                        lockA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "threadA").start();
        // 保证 threadA 执行lockA.wait();
        Thread.sleep(100);

        new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "获取 lockA, 并尝试获取 lockB");
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "获取 lockB");
                }
            }
        }, "threadB").start();
    }
}
