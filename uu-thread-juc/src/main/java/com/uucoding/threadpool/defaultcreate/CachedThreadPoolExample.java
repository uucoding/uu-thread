package com.uucoding.threadpool.defaultcreate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可缓存的线程池
 *
 * 特点：不断的创建线程
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  16:55
 */
public class CachedThreadPoolExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                // 观察线程编号
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
}
