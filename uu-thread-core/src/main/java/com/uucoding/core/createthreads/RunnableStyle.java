package com.uucoding.core.createthreads;

/**
 * 创建线程的方式：通过实现Runnable接口实现创建线程
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/18  21:59
 */
public class RunnableStyle implements Runnable {

    @Override
    public void run() {
        System.out.println("通过实现Runnable接口实现创建线程");
    }

    public static void main(String[] args) {
        new Thread(new RunnableStyle())
                .start();
    }
}
