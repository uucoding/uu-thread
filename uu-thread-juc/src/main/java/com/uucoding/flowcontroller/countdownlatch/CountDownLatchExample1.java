package com.uucoding.flowcontroller.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模式检查数据，全部检查通过，才可以提示检查结束
 * <p>
 * 一个线程等待多个线程都执行完毕，再继续自己的工作
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/8  00:26
 */
public class CountDownLatchExample1 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            int finalI = i + 1;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(new Random().nextInt(10000));
                    System.out.println("开始检查第 " + finalI + " 条数据");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            };
            executorService.submit(runnable);
        }
        System.out.println("等待3条数据检查");
        countDownLatch.await();
        System.out.println("检查结束");

    }
}
