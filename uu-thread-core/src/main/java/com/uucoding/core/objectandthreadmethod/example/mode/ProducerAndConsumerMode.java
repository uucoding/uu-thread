package com.uucoding.core.objectandthreadmethod.example.mode;

import java.util.LinkedList;

/**
 * 用wait和notify实现 生产者消费者模式
 * <p>
 * 使用{@link java.util.concurrent.BlockingQueue}实现生产者消费者模式示例{@link com.uucoding.core.stopthread.error.volatilestyle.CannotStopThreadByVolatileFix}
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/25  20:54
 */
public class ProducerAndConsumerMode {

    /**
     * 存放的队列
     */
    static class StorageQueue {

        /**
         * 最大存放值
         */
        private int maxSize;

        /**
         * 存储数据
         */
        private LinkedList<Object> storageList;

        public StorageQueue() {
            this.maxSize = 10;
            this.storageList = new LinkedList<>();
        }
        // --- put 和 take持有的是同一把锁，所以方法每次执行完都会主动释放掉

        /**
         * 存放数据
         *
         * @param o
         */
        public synchronized void put(Object o) {
            // 大于10个就停止生产，释放锁 (此处必须使用while，因为如果又多个生产者wait释放锁再重新获取锁的时候，直接执行wait之后的方法，而不会再判断storageList是否被其他线程填满，while则可以持续判断)
            while (storageList.size() == maxSize) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            storageList.add(o);
            System.out.println("生产数据 [ " + o + " ]， 现在仓库容量为：" + storageList.size());
            // 仓库有数据，立刻通知消费者消费
            notify();
        }

        /**
         * 消费数据
         *
         * @return
         */
        public synchronized void take() {
            // 如果全部消费完毕，立刻通知生产者生产
            while (storageList.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("---消费数据[ " + storageList.poll() + " ]， 现在仓库容量为：" + storageList.size());

            // 消费之后立刻通知生产者减少
            notify();
        }
    }

    /**
     * 生产者
     */
    static class Producer implements Runnable {

        StorageQueue storageQueue;

        public Producer(StorageQueue storageQueue) {
            this.storageQueue = storageQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storageQueue.put(i);
            }
        }
    }

    /**
     * 消费者
     */
    static class Consumer implements Runnable {

        StorageQueue storageQueue;

        public Consumer(StorageQueue storageQueue) {
            this.storageQueue = storageQueue;
        }

        /**
         * 消费一千个产品
         */
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storageQueue.take();
            }
        }
    }


    public static void main(String[] args) {
        StorageQueue storageQueue = new StorageQueue();
        Thread producer = new Thread(new Producer(storageQueue));
        producer.start();
        Thread consumer = new Thread(new Consumer(storageQueue));
        consumer.start();
    }
}
