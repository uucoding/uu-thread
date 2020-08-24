package com.uucoding.threadpool.defaultcreate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自动创建线程池：FixedThreadPool
 * <p>
 * 演示OOM的问题
 * <p>
 * 启动需要修改JVM参数，让当前案例尽快进入OOM
 * 设置堆大小 -Xms8m -Xmx8m
 * <p>
 * 抛出异常：Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "main"
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  15:53
 */
public class FixedThreadPoolExampleOOM {

    static class Task implements Runnable {

        @Override
        public void run() {
            // 任务中让其处理时间超长
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 提交超多任务
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new Task());
        }

    }
}

