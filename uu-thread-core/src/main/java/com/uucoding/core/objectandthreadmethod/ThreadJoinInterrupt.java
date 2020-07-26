package com.uucoding.core.objectandthreadmethod;

import java.util.concurrent.CountDownLatch;

/**
 * join中断方式：因为是在主线程中调用，所以中断的是主线程
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/26  15:41
 */
public class ThreadJoinInterrupt {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Runnable runnable = () -> {
            try {
                mainThread.interrupt();
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + "执行完毕！");
            } catch (InterruptedException e) {
                System.out.println("子线程执行中断");
                e.printStackTrace();
            }

        };
        Thread threadA = new Thread(runnable);
        System.out.println("开始启动子线程");
        threadA.start();
        // 等待 A线程执行完毕
        try {
            System.out.println("等待A线程执行完毕");
            threadA.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断");
            //此时只是中断了主线程，还需要告知子线程也中断你
            threadA.interrupt();
            e.printStackTrace();
        }
        System.out.println("子线程执行完毕");
    }
}
