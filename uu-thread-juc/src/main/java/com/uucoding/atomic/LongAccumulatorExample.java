package com.uucoding.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * 演示{@link java.util.concurrent.atomic.LongAccumulator}
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/1  01:05
 */
public class LongAccumulatorExample {

    public static void main(String[] args) {
        // (x, y) -> x + y, identity;
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

//        longAccumulator.accumulate(1);
        // 计算1 + ...+ 9
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(1, 10).forEach(i -> {
            executorService.submit(() -> longAccumulator.accumulate(i));
        });
        executorService.shutdown();
        while (!executorService.isTerminated()){}
        System.out.println(longAccumulator.get());
    }
}
