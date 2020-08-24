package com.uucoding.threadpool.defaultcreate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自动创建线程池：FixedThreadPool
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  15:53
 */
public class FixedThreadPoolExample {

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 执行了当前任务");
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }

    }
}

