package com.uucoding.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 演示原子数组类{@link java.util.concurrent.atomic.AtomicIntegerArray}
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/31  20:03
 */
public class AtomicArrayExample implements Runnable {

    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(100);

    /**
     * 操作原子类
     */
    public void operateAtomicArray() {

    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            operateAtomicArray();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Decrement decrement = new Decrement(atomicIntegerArray);
        Increment increment = new Increment(atomicIntegerArray);

        Thread threadA = new Thread(decrement);
        Thread threadB = new Thread(increment);

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }
    }
}

/**
 * 加操作
 */
class Increment implements Runnable {

    private AtomicIntegerArray atomicIntegerArray;

    public Increment(AtomicIntegerArray atomicIntegerArray) {
        this.atomicIntegerArray = atomicIntegerArray;
    }

    @Override
    public void run() {
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            atomicIntegerArray.getAndIncrement(i);
        }
    }
}

/**
 * 减操作
 */
class Decrement implements Runnable {
    private AtomicIntegerArray atomicIntegerArray;

    public Decrement(AtomicIntegerArray atomicIntegerArray) {
        this.atomicIntegerArray = atomicIntegerArray;
    }


    @Override
    public void run() {
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            atomicIntegerArray.getAndDecrement(i);
        }
    }
}
