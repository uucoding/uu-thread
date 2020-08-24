package com.uucoding.threadpool.stop;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 停止线程的方式： shutdownNow
 * 关闭线程池，会把队列中还没有执行的任务返回到一个集合中，已经在运行的线程会得到停止的同志
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  18:44
 */
public class StopThreadPoolShutDownNow {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
        Thread.sleep(1000);
        List<Runnable> runnables = executorService.shutdownNow();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 被中断了");
            }
        }
    }
}
