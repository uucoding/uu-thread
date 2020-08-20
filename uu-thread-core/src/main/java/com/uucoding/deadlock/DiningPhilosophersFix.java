package com.uucoding.deadlock;

import java.util.Random;

/**
 * 演示：哲学家就餐问题所导致的死锁
 * 哲学家只做两件事：思考和吃饭
 * 哲学家围绕圆桌，随机时间思考，随机拿起筷子吃饭，每个人左右两边都有一根筷子，每次吃饭都需要完全拿到左右两遍的筷子才可以吃饭
 * fix: 通过让其中一个哲学家改变拿筷子的顺序，破坏必然死锁的 循环等待条件
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/20  18:38
 */
public class DiningPhilosophersFix {


    // 哲学家人数
    private static final int NUM_PHILOSOPHER = 5;

    // 筷子根数
    private static final int NUM_CHOPSTICK = NUM_PHILOSOPHER;

    public static void main(String[] args) {
        // 初始化筷子的数量
        Object[] chopsticks = new Object[NUM_PHILOSOPHER];
        for (int i = 0; i < NUM_CHOPSTICK; i++) {
            chopsticks[i] = new Object();
        }
        // 初始化哲学家，并安排筷子的位置
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHER];
        for (int i = 0; i < NUM_PHILOSOPHER; i++) {
            // 左边的筷子
            Object leftChopstick = chopsticks[i];
            // 右边的筷子
            Object rightChopstick = chopsticks[i];

            // 最后一位，改变拿筷子的顺序
            if (i== NUM_PHILOSOPHER -1) {
                philosophers[i] = new Philosopher(rightChopstick, leftChopstick);
            } else {
                philosophers[i] = new Philosopher(leftChopstick, rightChopstick);
            }
            // 启动哲学家的线程
            new Thread(philosophers[i], "哲学家" + (i + 1)).start();
        }

    }

    /**
     * 哲学家要做的事情只有吃饭和思考
     */
    static class Philosopher implements Runnable{
        Random random = new Random();

        /**
         * 左边的筷子
         */
        private Object left;

        /**
         * 右边的筷子
         */
        private Object right;

        public Philosopher(Object left, Object right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            while (true) {
                // 随机思考的时间
                try {
                    doAction("开始思考");
                    // 拿筷子吃饭，要求先拿左边的再拿右边的筷子配成一双才能吃饭
                    synchronized (left) {
                        doAction("拿 左 边的筷子");
                        synchronized (right) {
                            doAction("拿 右 边的筷子，开始吃饭");
                            doAction("放下 右 边的筷子");
                        }
                        doAction("放下 左 边的筷子");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void doAction(String action) throws InterruptedException{
            System.out.println(Thread.currentThread().getName() + "  " + action);
            Thread.sleep(random.nextInt(10));

        }
    }
}
