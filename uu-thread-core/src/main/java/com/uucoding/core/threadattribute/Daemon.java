package com.uucoding.core.threadattribute;

/**
 * 演示：程序只有守护线程时，系统会自动退出
 *
 * 用户线程执行完毕，守护线程即便还没有执行完毕，也会直接退出
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/27  21:50
 */
public class Daemon {

    public static void main(String[] args) {
        Thread commonThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 打印 " + i);
            }
        }, "用户线程");

        commonThread.start();
        // 守护线程
        Thread daemonThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 打印 " + i);
            }
        }, "守护线程");
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}
