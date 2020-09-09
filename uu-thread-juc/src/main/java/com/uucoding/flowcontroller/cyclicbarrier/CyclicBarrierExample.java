package com.uucoding.flowcontroller.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * {@link CyclicBarrier}演示
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/10  01:56
 */
public class CyclicBarrierExample {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("到齐了5个线程，可以进行下一步操作了！");
        });

        for (int i = 0; i < 10; i++) {
            new Thread(new Task(i, cyclicBarrier)).start();
        }
    }

    static class Task implements Runnable {
        private int id;
        private CyclicBarrier cyclicBarrier;

        public Task(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程 " + id + " 出发！");
            try {
                Thread.sleep(new Random().nextInt(10000));
                System.out.println("线程 " + id + " 到达指定地点，等待其他人！");
                cyclicBarrier.await();

                System.out.println("线程 " + id + " 开始执行下一步操作了！");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }
}
