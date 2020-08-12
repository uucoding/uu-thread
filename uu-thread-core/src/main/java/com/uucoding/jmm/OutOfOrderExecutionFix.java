package com.uucoding.jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 使用volatile关键字禁止重排序
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/2  21:13
 */
public class OutOfOrderExecutionFix {

    private volatile static int a = 0, b = 0;
    private volatile static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        while (true) {
            // 计数器，设置计数器次数，每次执行countDown()则进行一次减1，次数为0的时候才可以执行await之后的代码
            CountDownLatch countDownLatch = new CountDownLatch(1);
            // 重新初始化
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            Thread threadA = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            });

            Thread threadB = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = 1;
                y = a;
            });
            threadA.start();
            threadB.start();
            // 防止主线程优于A和B子线程执行
//            Thread.sleep(1);
            // 计数器-1
            countDownLatch.countDown();
            threadA.join();
            threadB.join();
            // ----- 输出情况----------
            // OutOfOrderExecution  B线程先执行完，A线程后执行
            // x = 0, y = 1  A线程先执行完，B线程后执行
            // x = 1, y = 1  (达到想要的执行结果可能比较耗时)A线程执行到a=1的时候，切换到B线程执行b=1,最后执行x = b, y = b
            // x = 0, y = 0  这种结果不会出现，因为不存在重排序，且volatile也保证了可见性
            System.out.println("第" + count++ + "执行， 输出 x = " + x + ", y = " + y);
            if (x == 0 && y == 0) {
                break;
            }
        }
    }
}
