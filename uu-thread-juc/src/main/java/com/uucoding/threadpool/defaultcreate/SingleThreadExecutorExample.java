package com.uucoding.threadpool.defaultcreate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池只有一个线程
 * Executors.newSingleThreadExecutor
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  16:13
 */
public class SingleThreadExecutorExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
