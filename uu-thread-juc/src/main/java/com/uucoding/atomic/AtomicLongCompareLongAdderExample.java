package com.uucoding.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 演示{@link java.util.concurrent.atomic.AtomicLong} 和 {@link java.util.concurrent.atomic.LongAdder} 效率对比
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/1  00:33
 */
public class AtomicLongCompareLongAdderExample {

    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong();

        ExecutorService executor = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executor.submit(new AtomicLongTask(atomicLong));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
        long end = System.currentTimeMillis();

        System.out.println("AtomicLong 执行时间为：" + (end - start));
        System.out.println("AtomicLong 执行结果为：" + atomicLong.get());
        //  执行LongAdder
        LongAdder longAdder = new LongAdder();
        ExecutorService executor2 = Executors.newFixedThreadPool(20);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executor2.submit(new LongAdderTask(longAdder));
        }
        executor2.shutdown();
        while (!executor2.isTerminated()) {}
        long end2 = System.currentTimeMillis();
        System.out.println("LongAdder 执行时间为：" + (end2 - start2));
        System.out.println("LongAdder 执行结果为：" + longAdder.sum());

    }


    /**
     * AtomicLong任务
     */
    static class AtomicLongTask implements Runnable{
        private AtomicLong atomicLong;

        public AtomicLongTask(AtomicLong atomicLong) {
            this.atomicLong = atomicLong;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                atomicLong.getAndIncrement();
            }
        }
    }

    /**
     * AtomicLong任务
     */
    static class LongAdderTask implements Runnable{
        private LongAdder longAdder;

        public LongAdderTask(LongAdder longAdder) {
            this.longAdder = longAdder;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                longAdder.increment();
            }
        }
    }
}
