package com.uucoding.jmm;

/**
 * volatile适合纯赋值操作
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/12  19:08
 */
public class VolatileSuitAssignValue implements Runnable{
    volatile boolean done = false;
    @Override
    public void run() {
        for (int index = 0; index < 1000; index++) {
            // 纯赋值操作
            this.done = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileSuitAssignValue volatileNotSuitDependOldStatus = new VolatileSuitAssignValue();
        Thread threadA = new Thread(volatileNotSuitDependOldStatus);
        Thread threadB = new Thread(volatileNotSuitDependOldStatus);
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        System.out.println(volatileNotSuitDependOldStatus.done); // true
    }
}
