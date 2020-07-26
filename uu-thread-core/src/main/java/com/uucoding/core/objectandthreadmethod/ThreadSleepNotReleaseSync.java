package com.uucoding.core.objectandthreadmethod;

/**
 * 演示 sleep不会释放synchronized锁
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/26  11:26
 */
public class ThreadSleepNotReleaseSync implements Runnable{

    @Override
    public void run() {
        synchronized (this) {
            System.out.println("线程：" + Thread.currentThread().getName() + "获取了锁！");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + " 执行完毕！");
        }
    }

    public static void main(String[] args) {
        ThreadSleepNotReleaseSync threadSleepNotReleaseSync = new ThreadSleepNotReleaseSync();
        new Thread(threadSleepNotReleaseSync).start();
        new Thread(threadSleepNotReleaseSync).start();
    }
}
