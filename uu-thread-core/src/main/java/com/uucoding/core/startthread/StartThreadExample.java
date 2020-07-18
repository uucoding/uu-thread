package com.uucoding.core.startthread;

/**
 * 启动线程-> start() 和run()方法的比较
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/18  23:50
 */
public class StartThreadExample {

    public static void main(String[] args) {
        //实现runnable接口
        Runnable runnable = () -> System.out.println("当前线程名称为：" + Thread.currentThread().getName());
        // 定义一个线程
        Thread thread = new Thread(runnable);
        thread.run(); // output: 当前线程名称为：main
        thread.start(); // output: 当前线程名称为：当前线程名称为：Thread-0
    }
}
