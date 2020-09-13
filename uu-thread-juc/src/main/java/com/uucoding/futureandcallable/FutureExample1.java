package com.uucoding.futureandcallable;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示：{@link Future} 和 {@link Callable} 案例1
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/13  18:03
 */
public class FutureExample1 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> future = executorService.submit(new Task());

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);

            return new Random().nextInt();
        }
    }
}
