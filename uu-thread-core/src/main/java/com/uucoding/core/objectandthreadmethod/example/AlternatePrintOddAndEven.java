package com.uucoding.core.objectandthreadmethod.example;

/**
 * 交替打印0-100的奇偶数字：使用synchronized
 *
 * 这种方式会造成资源浪费，因为有可能一直是其中一个线程拿到锁，但是如果不对对应的打印值，那么就只能不停的循环处理，直到切换到另一个线程
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/25  22:39
 */
public class AlternatePrintOddAndEven {

    private static Object locked = new Object();
    private static int count = 0;

    public static void main(String[] args) {

        // 偶数
        new Thread(() -> {
            while (count < 100) {
                synchronized (locked) {
                    if (count % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + " : " + count++);
                    }
                }
            }
        }, "偶数线程").start();

        //奇数
        new Thread(() -> {
            while (count < 100) {
                synchronized (locked) {
                    if (count % 2 != 0) {
                        System.out.println(Thread.currentThread().getName() + " : " + count++);
                    }
                }
            }
        }, "奇数线程").start();
    }
}
