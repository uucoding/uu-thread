package com.uucoding.threadpool.defaultcreate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  16:55
 */
public class ScheduledThreadPoolExample {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        // 延迟五秒后执行
        scheduledExecutorService.schedule(() -> System.out.println("schedule " + Thread.currentThread().getName()),
                5, TimeUnit.SECONDS);

        // 第一次延迟1秒后执行，之后每隔5秒重复执行
        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("scheduleAtFixedRate " + Thread.currentThread().getName()),
                3, 5, TimeUnit.SECONDS);
    }
}
