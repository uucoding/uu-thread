package com.uucoding.core.startthread;

/**
 * 调用两次start方法
 * 抛出异常: IllegalThreadStateException
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/19  00:41
 */
public class StartThreadTwice {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(runnable);
        thread.start(); // Thread-0
        thread.start(); // throw java.lang.IllegalThreadStateException
    }
}
