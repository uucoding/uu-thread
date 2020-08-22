package com.uucoding.synchronized_;

/**
 * 演示不使用并发控制带来的后果 a++不能等于20000
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/22  19:43
 */
public class DisappearNumberFix implements Runnable {

    static int number = 0;

    @Override
    public void run() {
        // 修复消失的次数
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                number++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        DisappearNumberFix disappearNumber = new DisappearNumberFix();

        Thread a = new Thread(disappearNumber);
        Thread b = new Thread(disappearNumber);
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println(number);
    }
}
