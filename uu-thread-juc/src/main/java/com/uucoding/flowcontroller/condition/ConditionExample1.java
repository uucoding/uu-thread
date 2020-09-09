package com.uucoding.flowcontroller.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link java.util.concurrent.locks.Condition}基本用法
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/10  00:54
 */
public class ConditionExample1 {

    static Lock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    void waitMethod() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 进入等待");
            condition.await();
            System.out.println(Thread.currentThread().getName() + " 开始执行");
        } finally {
            lock.unlock();
        }
    }

    void releaseMethod() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 执行完毕，通知等待的线程可以执行了");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ConditionExample1 conditionExample1 = new ConditionExample1();


        new Thread(() -> {
            try {
                Thread.sleep(1000);
                conditionExample1.releaseMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        // 进入等待
        conditionExample1.waitMethod();
    }
}
