package com.uucoding.core.objectandthreadmethod;

/**
 * join 的普通用法：等待新线程执行完毕
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/26  15:41
 */
public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕！");
        };
        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);
        System.out.println("开始启动子线程");
        threadA.start();
        threadB.start();
        // 等待 AB线程执行完毕
        threadA.join();
        threadB.join();
        System.out.println("子线程执行完毕");
    }
}
