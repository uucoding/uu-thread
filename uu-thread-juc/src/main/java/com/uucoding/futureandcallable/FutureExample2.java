package com.uucoding.futureandcallable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示：{@link Future} 和 {@link Callable} 案例2
 *
 * 多个任务，用Future数组来获取结果
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/13  18:03
 */
public class FutureExample2 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = executorService.submit(new Task());

            futureList.add(future);
        }

        for (Future<Integer> taskFuture : futureList) {
            try {
                System.out.println(taskFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
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
