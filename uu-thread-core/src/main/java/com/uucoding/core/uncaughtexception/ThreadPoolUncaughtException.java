package com.uucoding.core.uncaughtexception;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池的异常处理
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/27  16:10
 */
public class ThreadPoolUncaughtException {
    static ArrayBlockingQueue<Runnable> linkedBlockingQueue = new ArrayBlockingQueue<Runnable>(500);
    static AtomicInteger threadNum = new AtomicInteger(1);
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 100, 2, TimeUnit.HOURS,
            linkedBlockingQueue, r -> {
        Thread thread = new Thread(r);
        thread.setName("线程-" + threadNum.getAndIncrement());
        thread.setUncaughtExceptionHandler((th, e) -> {
            System.out.println(th.getName() + "异常");
            e.printStackTrace();
        });
        return thread;
    });
    private  static  Object object = new Object();

    public static void main(String[] args) throws Exception {
        List<Future<Integer>> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < 2000; i++) {
            // 队列数据超过490个的时候，休眠一秒消耗一下，否则队列的数据直接塞满报错
            if (linkedBlockingQueue.size() == 490) {
                Thread.sleep(1000);
            }
            int finalI = i;
            Future<Integer> submit = executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "  " + finalI + "..." + linkedBlockingQueue.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return finalI;
            });
            list.add(submit);

        }
        for (Future<Integer> future : list) {
            // 在此处可以获取到子线程抛出的相关异常
            System.out.println(future.get());
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间为： " + (end - start) / 1000);
        executor.shutdown();
    }
}
