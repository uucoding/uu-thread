package com.uucoding.core.uncaughtexception;

/**
 * 使用传统的try...catch无法捕获子线程的异常
 *
 * try...catch只能捕获本线程的异常，不能捕获子线程异常
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/27  22:33
 */
public class CannotCatchChildException {

    public static void main(String[] args) {
        Runnable runnable  = () -> {
            throw new RuntimeException(Thread.currentThread().getName() + " 发生异常");
        };
        try {
            // 两个线程会连续抛出异常，而不是第一个抛出后被捕获
            new Thread(runnable).start();
            new Thread(runnable).start();
        } catch (Exception e) {
            System.out.println("这里准备捕获子线程异常");
        }

    }
}
