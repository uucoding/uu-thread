package com.uucoding.core.stopthread.error.volatilestyle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 演示修复{@link CannotStopThreadByVolatile}示例出现的问题
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  21:13
 */
public class CannotStopThreadByVolatileFix {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);

        ProducerFix producerFix = new ProducerFix(blockingQueue);
        Thread thread = new Thread(producerFix);
        thread.start();
        //休息一秒钟之后，目的是为了让生产者将队列塞满
        Thread.sleep(1000);

        //消费者开始消费
        ConsumerFix consumerFix = new ConsumerFix(blockingQueue);

        //消费，并判断是否需要继续消费
        while (consumerFix.hasNeedNext()) {
            System.out.println("消费了数据：" + consumerFix.consumeQueue.take());
            // 每次消费消耗1秒钟
            Thread.sleep(1000);
        }
        System.out.println("消费者不需要更多的数据了");

        // 消费者不消费了，生产者中断线程
        // producer.canceled = true;
        // fix:
        thread.interrupt();
    }
}

/**
 * 生产者
 */
class ProducerFix implements Runnable {

    /**
     * 生产者的存储队列
     */
    BlockingQueue<Integer> storageQueue;

    public ProducerFix(BlockingQueue<Integer> storageQueue) {
        this.storageQueue = storageQueue;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (num < 1000 && !Thread.currentThread().isInterrupted()) {
                //这一行的作用是标记作用
                System.out.println("---");
                storageQueue.put(num);
                System.out.println("第 " + num + " 输出，并存入storageQueue仓库中");
                Thread.sleep(50);
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 通过发送中断指令，此处可以被执行到
            System.out.println("生产者停止运行");
        }
    }
}

/**
 * 消费者
 */
class ConsumerFix {
    /**
     * 消费者的消费仓库->对应生产者的仓库
     */
    BlockingQueue<Integer> consumeQueue;

    public ConsumerFix(BlockingQueue<Integer> consumeQueue) {
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