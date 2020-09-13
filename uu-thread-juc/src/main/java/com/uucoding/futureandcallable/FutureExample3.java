package com.uucoding.futureandcallable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示：{@link Future} 和 {@link Callable} 案例3
 *
 * get()方法抛出异常 和isDone演示
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/13  18:03
 */
public class FutureExample3 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<Integer> future = executorService.submit(new Task());

        try {
            Thread.sleep(1000);
            System.out.println(future.isDone());
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException 异常");
        } catch (ExecutionException e) {
            e.printStackTrace();
            // get方法抛出异常都是ExecutionException
            System.out.println("ExecutionException 异常");
        }
        executorService.shutdown();
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            throw new ArrayIndexOutOfBoundsException("抛出异常");
        }
    }
}
