package com.uucoding.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用阻塞队列演示生产和消费
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/7  22:57
 */
public class BlockingQueueExample {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        Producer producer = new Producer(blockingQueue);
        Customer customer = new Customer(blockingQueue);

        new Thread(producer).start();
        new Thread(customer).start();
    }

}

class Customer implements Runnable {

    private ArrayBlockingQueue<String> blockingQueue;

    public Customer(ArrayBlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String take;
        try {
            while (!(take = blockingQueue.take()).equals("stop")) {
                System.out.println("消耗子项：" + take);
            }
            System.out.println("消耗数据完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 容器
 */
class Producer implements Runnable {

    private BlockingQueue<String> blockingQueue;

    public Producer(ArrayBlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("设置子项：" + i);
                blockingQueue.put("子项" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            blockingQueue.put("stop");
            System.out.println("设置数据完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
