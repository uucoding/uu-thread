package com.uucoding.flowcontroller.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 5人比赛，发令枪响，五人开始跑步
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/9  21:35
 */
public class CountDownLatchExample2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int finalI = i + 1;
            Runnable runnable = () -> {
                System.out.println("No." + finalI + " 准备完毕");
                try {
                    latch.await();
                    System.out.println("No." + finalI + " 起跑");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executorService.submit(runnable);
        }

        Thread.sleep(3000);
        System.out.println("发令枪响，可以跑步了！");
        latch.countDown();
        executorService.shutdown();
    }
}
