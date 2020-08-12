package com.uucoding.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile不适合i++的场景
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/12  19:08
 */
public class VolatileNotSuitIPlusPlus implements Runnable{
    volatile int i = 0;
    AtomicInteger atomicI = new AtomicInteger();
    @Override
    public void run() {
        for (int index = 0; index < 1000; index++) {
            i++;
            atomicI.getAndIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileNotSuitIPlusPlus volatileNotSuitIPlusPlus = new VolatileNotSuitIPlusPlus();
        new Thread(volatileNotSuitIPlusPlus).start();
        new Thread(volatileNotSuitIPlusPlus).start();
        // 等待1秒，看结果
        Thread.sleep(1000);
        System.out.println(volatileNotSuitIPlusPlus.i); // 值不固定
        System.out.println(volatileNotSuitIPlusPlus.atomicI.get()); // 2000

    }
}
