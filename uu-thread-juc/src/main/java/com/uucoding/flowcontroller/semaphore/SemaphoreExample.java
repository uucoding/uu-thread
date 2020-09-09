package com.uucoding.flowcontroller.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量{@link java.util.concurrent.Semaphore} 演示
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/9  23:53
 */
public class SemaphoreExample {

    static Semaphore semaphore = new Semaphore(5, true);

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 40; i++) {
            executorService.submit(new Task());
        }

        executorService.shutdown();

    }

    static class Task implements Runnable {

        @Override
        public void run() {

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 获取到许可证，准备执行");
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕，归还许可证");
            semaphore.release();

        }
    }
}


