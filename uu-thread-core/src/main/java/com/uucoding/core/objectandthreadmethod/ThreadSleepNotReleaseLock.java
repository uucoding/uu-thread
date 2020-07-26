package com.uucoding.core.objectandthreadmethod;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示：sleep不会释放Lock锁（lock本身就需要手动释放）
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/26  11:30
 */
public class ThreadSleepNotReleaseLock implements Runnable{

    private static final Lock LOCK = new ReentrantLock();

    @Override
    public void run() {
        LOCK.lock();
        try {
            System.out.println("线程：" + Thread.currentThread().getName() + "获取了锁！");
            Thread.sleep(1000);
            // 等价于Thread.sleep(1000)，更建议使用如下方案，可以免于多种计算，且传入小于0的数据不会处理
            // TimeUnit.SECONDS.sleep(1);
            System.out.println("线程：" + Thread.currentThread().getName() + " 执行完毕！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
          LOCK.unlock();
        }
    }

    public static void main(String[] args) {
        ThreadSleepNotReleaseLock threadSleepNotReleaseLock = new ThreadSleepNotReleaseLock();
        new Thread(threadSleepNotReleaseLock).start();
        new Thread(threadSleepNotReleaseLock).start();
    }
}
