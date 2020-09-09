package com.uucoding.flowcontroller.condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link Condition}实现生产者消费者模式
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/10  00:54
 */
public class ConditionExample2 {
    static int queueSize = 10;
    static Lock lock = new ReentrantLock();

    static Condition fullCondition = lock.newCondition();

    static Condition emptyCondition = lock.newCondition();

    static PriorityQueue<Integer> queue = new PriorityQueue(queueSize);

    public static void main(String[] args) throws InterruptedException {
        new Consumer().start();

        new Producer().start();


    }

    /**
     * 消费者
     */
    static class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {

                    while (queue.size() == 0) {
                        // 如果队列里面为空，那么消费者等待
                        try {
                            System.out.println("队列空了，等待生产");
                            emptyCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("消费者消费了一个，此时队列还剩下 " + queue.size() + " 条数据");
                    // 消费了就立刻通知生产者生产
                    fullCondition.signal();
                } finally {
                    lock.unlock();
                }
            }

        }
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        // 如果队列里面为满了，那么生产者等待
                        try {
                            System.out.println("队列满了，等待消费");
                            fullCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);

                    System.out.println("生产者生产了一个，此时队列还有剩余空间 " + (queueSize - queue.size()));
                    // 生产了就立刻通知消费者消费
                    emptyCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
