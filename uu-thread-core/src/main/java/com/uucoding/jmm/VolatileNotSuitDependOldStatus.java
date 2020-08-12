package com.uucoding.jmm;

/**
 * volatile不适合依赖变量之前状态的场景
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/12  19:08
 */
public class VolatileNotSuitDependOldStatus implements Runnable {
    volatile boolean done = false;

    @Override
    public void run() {
        for (int index = 0; index < 1000; index++) {
            // 赋值依赖之前的done状态
            this.done = !this.done;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            VolatileNotSuitDependOldStatus volatileNotSuitDependOldStatus = new VolatileNotSuitDependOldStatus();
            Thread threadA = new Thread(volatileNotSuitDependOldStatus);
            Thread threadB = new Thread(volatileNotSuitDependOldStatus);
            threadA.start();
            threadB.start();
            threadA.join();
            threadB.join();
            System.out.println(volatileNotSuitDependOldStatus.done); // 值不固定
            if (volatileNotSuitDependOldStatus.done) {
                break;
            }
        }


    }
}
