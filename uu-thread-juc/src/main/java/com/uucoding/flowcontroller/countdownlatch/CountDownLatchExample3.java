package com.uucoding.flowcontroller.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 5人比赛，发令枪响，五人开始跑步，全部到终点 宣布比赛结束
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/9  21:35
 */
public class CountDownLatchExample3 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int finalI = i + 1 ;
            Runnable runnable = () -> {
                System.out.println("No." + finalI + " 准备完毕");
                try {
                    begin.await();
                    System.out.println("No." + finalI + " 起跑");
                    Thread.sleep(new Random().nextInt(10000));
                    System.out.println("No." + finalI + " 到终点");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            };
            executorService.submit(runnable);
        }
        executorService.shutdown();
        Thread.sleep(3000);
        System.out.println("发令枪响，可以跑步了！");
        begin.countDown();
        end.await();
        System.out.println("所有人跑完，比赛结束！");
    }
}
