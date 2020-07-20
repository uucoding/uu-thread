package com.uucoding.core.stopthread.error.volatilestyle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 演示：当陷入长时间阻塞的时候，无法通过volatile关键字修饰boolean属性 进行中断线程
 * <p>
 * <p>
 * 此例：采用生产者消费者模式，生产者的生产速度大于消费者的消费速度，所以会出现阻塞队列满的情况，
 * 一旦满了，生产者便会陷入阻塞，等待消费者进一步消费
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  21:13
 */
public class CannotStopThreadByVolatile {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);

        Producer producer = new Producer(blockingQueue);
        Thread thread = new Thread(producer);
        thread.start();
        //休息一秒钟之后，目的是为了让生产者将队列塞满
        Thread.sleep(1000);

        //消费者开始消费
        Consumer consumer = new Consumer(blockingQueue);

        //消费，并判断是否需要继续消费
        while (consumer.hasNeedNext()) {
            System.out.println("消费了数据："+ consumer.consumeQueue.take());
            // 每次消费消耗1秒钟
            Thread.sleep(1000);
        }
        System.out.println("消费者不需要更多的数据了");

        // 消费者不消费了，生产者中断线程
        producer.canceled = true;
        // 实际上，并不会中断线程，
    }
}

/**
 * 生产者
 */
class Producer implements Runnable {

    volatile boolean canceled = false;

    /**
     * 生产者的存储队列
     */
    BlockingQueue<Integer> storageQueue;

    public Producer(BlockingQueue<Integer> storageQueue) {
        this.storageQueue = storageQueue;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (num < 1000 && !canceled) {
                //这一行的作用是标记作用
                System.out.println("---");
                // 中断后，程序会卡在这一行
                storageQueue.put(num);
                System.out.println("第 " + num + " 输出，并存入storageQueue仓库中");
                Thread.sleep(50);
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 这一行并没被执行到
            System.out.println("生产者停止运行");
        }
    }
}

/**
 * 消费者
 */
class Consumer {
    /**
     * 消费者的消费仓库->对应生产者的仓库
     */
    BlockingQueue<Integer> consumeQueue;

    public Consumer(BlockingQueue<Integer> consumeQueue) {
        this.consumeQueue = consumeQueue;
    }

    /**
     * 判断是否还继续消费
     *
     * @return
     */
    public boolean hasNeedNext() {
        // 如果随机数小于0.9则继续获取下一个数据
        return Math.random() < 0.9;
    }
}