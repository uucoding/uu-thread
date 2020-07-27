package com.uucoding.core.uncaughtexception;

/**
 * 异常在子线程中，抛出后，主线程并不会终止，还是会直接运行
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/27  22:30
 */
public class ExceptionInChildThread {

    public static void main(String[] args) {
        new Thread(() -> {
            throw new RuntimeException("子线程异常");
        }).start();

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
