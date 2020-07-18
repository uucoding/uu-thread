package com.uucoding.core.createthreads;

/**
 * 两种创建方式同时使用
 *
 * 结果描述：继承Thread，重写run方法会覆盖本身的run方法，导致target.run()丢失，所以runnable实例的run方法无法执行
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/18  22:38
 */
public class BothRunnableAndThread {

    public static void main(String[] args) {
        new Thread(() -> System.out.println("传入Runnable实现")) {
            @Override
            public void run() {
                System.out.println("重写了Thread的run方法");
            }
        }.start();
    }
}
