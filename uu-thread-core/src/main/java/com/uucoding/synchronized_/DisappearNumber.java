package com.uucoding.synchronized_;

/**
 * 演示不使用并发控制带来的后果 a++不能等于20000
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/22  19:43
 */
public class DisappearNumber implements Runnable {

    static int number = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            number++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        DisappearNumber disappearNumber = new DisappearNumber();

        Thread a = new Thread(disappearNumber);
        Thread b = new Thread(disappearNumber);
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println(number);
    }
}
