package com.uucoding.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义工具类，基于AQS实现
 * <p>
 * 功能：提供一个await方法让线程进入等待，提供一个release()方法，放行所有线程
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/11  01:14
 */
public class CustomAQSExample {

    private final Sync sync = new Sync();

    /**
     * 等待
     */
    public void await() {
        sync.acquireShared(0);
    }

    /**
     * 放行
     */
    public void release() {
        sync.releaseShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {

        /**
         * 判断是否能获取
         *
         * @param arg
         * @return
         */
        @Override
        protected int tryAcquireShared(int arg) {
            // state = 1 的时候，表示允许放行
            return getState() == 1 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CustomAQSExample customAQSExample = new CustomAQSExample();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 进入等待");
                customAQSExample.await();
                System.out.println(Thread.currentThread().getName() + " 开始执行");
            }).start();
        }

        Thread.sleep(100);
        System.out.println("休眠5秒，再放行");
        Thread.sleep(5000);
        customAQSExample.release();
        Thread.sleep(100);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 放行后，新加入的线程 进入等待");
            customAQSExample.await();
            System.out.println(Thread.currentThread().getName() + " 放行后，新加入的线程 开始执行");
        }).start();
    }
}
