package com.uucoding.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * 死锁代码检测 使用ThreadMXBean类可以检查到相关的线程死锁
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/19  01:02
 */
public class ThreadMXBeanDetection implements Runnable{

        static Object o1 = new Object();
        static Object o2 = new Object();
        int num = 0;
        @Override
        public void run() {
            if (num == 1) {
                synchronized (o1) {
                    System.out.println("线程A 获取o1锁");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println("线程A 获取o2锁");
                    }
                }
                System.out.println("线程A 执行完毕");
            }

            if (num == 2) {
                synchronized (o2) {
                    System.out.println("线程B 获取o2锁");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println("线程B 获取o1锁");
                    }
                }
                System.out.println("线程B 执行完毕");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ThreadMXBeanDetection threadMXBeanDetectionA = new ThreadMXBeanDetection();
            threadMXBeanDetectionA.num = 1;
            ThreadMXBeanDetection threadMXBeanDetectionB = new ThreadMXBeanDetection();
            threadMXBeanDetectionB.num = 2;
            new Thread(threadMXBeanDetectionA).start();
            new Thread(threadMXBeanDetectionB).start();
            TimeUnit.SECONDS.sleep(1);
            // 获取检测死锁的bean
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            // 查到死锁的id
            long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();

            for (long deadlockedThread : deadlockedThreads) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThread);
                System.out.println("发生死锁的线程为： " + threadInfo.getThreadName() + threadInfo.toString());
            }
        }
    }
