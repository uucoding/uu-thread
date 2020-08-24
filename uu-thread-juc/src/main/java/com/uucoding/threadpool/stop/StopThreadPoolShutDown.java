package com.uucoding.threadpool.stop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 停止线程的方式： shutdown
 * 队列里面有任务会等待队列里面的任务全部执行完毕 才会彻底关掉
 * 关闭后再次提交任务，抛出异常：java.util.concurrent.RejectedExecutionException
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  18:44
 */
public class StopThreadPoolShutDown {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
        System.out.println("线程池当前状态： " + executorService.isShutdown());
        // 关闭线程池，队列里面有任务会等待队列里面的任务全部执行完毕 才会彻底关掉
        executorService.shutdown();
        // 关闭后再次提交，抛出异常：java.util.concurrent.RejectedExecutionException
        try {
            executorService.execute(new Task());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("调用shutdown后线程池当前状态： " + executorService.isShutdown());

        System.out.println("任务是否执行完毕 " + executorService.isTerminated());

        // 休眠1秒后查看线程中任务池是否关闭
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务是否执行完毕且线程池关闭 " + executorService.isTerminated());

    }

    static class Task implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
}
