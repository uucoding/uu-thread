package com.uucoding.jmm;

/**
 * 演示可见性问题
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/2  22:40
 */
public class FieldVisibility {

    int a = 1;
    int b = 2;

    public void write() {
        a = 3;
        b = a;
    }

    public void read() {
        /**
         * 输出的情况包含四种
         * a=3, b=3
         * a=1, b=2
         * a=3, b=2  这种情况可能是由于可见性导致，也可能是由于线程切换执行导致
         * a=1, b=3  这种情况是由于可见性导致，即a赋值后没有及时刷新到主存，导致B线程无法读取正确的值
         */
        System.out.println("a=" + a + ", b=" + b);
    }

    public static void main(String[] args) {

        while (true) {
            FieldVisibility fieldVisibility = new FieldVisibility();
            //写操作
            Thread threadA = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fieldVisibility.write();
            });
            threadA.start();
            // 读操作
            Thread threadB = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fieldVisibility.read();
            });
            threadB.start();
        }

    }
}
