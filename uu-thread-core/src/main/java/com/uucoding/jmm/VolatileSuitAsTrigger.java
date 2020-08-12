package com.uucoding.jmm;

/**
 * 作为刷新之前变量的触发器
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/2  22:40
 */
public class VolatileSuitAsTrigger {

    int a = 1;

    int b = 2;

    volatile int c = 3;

    public void write() {
        a = 3;
        //
        b = a;
        //  此处c作为触发器，根据hb原则，当c等于0的时候，a和b一定是可见的
        c = 0;
    }

    public void read() {
        // 当volatile 修饰的c == 0的时候， c之前的操作一定是可见的
        while (c != 0) {
            continue;
        }
        System.out.println("a=" + a + ", b=" + b);
    }

    public static void main(String[] args) {

        while (true) {
            VolatileSuitAsTrigger volatileSuitAsTrigger = new VolatileSuitAsTrigger();
            //写操作
            Thread threadA = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                volatileSuitAsTrigger.write();
            });
            threadA.start();
            // 读操作
            Thread threadB = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                volatileSuitAsTrigger.read();
            });
            threadB.start();
        }

    }
}
