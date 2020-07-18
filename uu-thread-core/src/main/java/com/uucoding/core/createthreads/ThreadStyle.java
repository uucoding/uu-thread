package com.uucoding.core.createthreads;

/**
 * 创建线程的方式：通过继承Thread实现线程
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/18  22:01
 */
public class ThreadStyle extends Thread {

    @Override
    public void run() {
        System.out.println("通过继承Thread实现线程");
    }

    public static void main(String[] args) {
        new ThreadStyle().start();
    }
}
