package com.uucoding.futureandcallable;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示：{@link FutureTask} 案例
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/13  18:03
 */
public class FutureTaskExample {

    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);

        // 启动单个线程执行
//        new Thread(integerFutureTask).start();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(integerFutureTask);

        try {
            System.out.println(integerFutureTask.get());
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
