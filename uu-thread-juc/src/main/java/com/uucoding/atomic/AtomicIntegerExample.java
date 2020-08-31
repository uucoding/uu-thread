package com.uucoding.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示原子类{@link java.util.concurrent.atomic.AtomicInteger}
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/31  20:03
 */
public class AtomicIntegerExample implements Runnable {

    static AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 操作原子类
     */
    public void operateAtomic() {
        // 自增
//        atomicInteger.getAndIncrement();
        // 加指定的值
        atomicInteger.getAndAdd(10);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            operateAtomic();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerExample atomicIntegerExample = new AtomicIntegerExample();

        Thread threadA = new Thread(atomicIntegerExample);
        Thread threadB = new Thread(atomicIntegerExample);

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
        System.out.println(AtomicIntegerExample.atomicInteger.get());
    }
}
