package com.uucoding.threadpool.customhookthreadpool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以暂停的线程池
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/23  19:10
 */
public class PauseThreadPool extends ThreadPoolExecutor {

    private boolean pause;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 线程执行任务之前的操作
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        lock.lock();
        try {
            while (pause) {
                // condition 于lock绑定提供线程等待相关操作
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        lock.lock();
        try {
            pause = true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 恢复
     */
    public void resume() {
        lock.lock();
        try {
            pause = false;
            // 通知所有线程恢复
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PauseThreadPool pauseThreadPool = new PauseThreadPool(10, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 100; i++) {
            pauseThreadPool.execute(runnable);
        }
        Thread.sleep(1000);
        pauseThreadPool.pause();
        System.out.println("线程池暂停工作，休息2s");
        Thread.sleep(2000);

        pauseThreadPool.resume();
        System.out.println("线程池恢复工作");
    }
}
