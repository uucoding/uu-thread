package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 饿汉式 （静态代码块） 线程安全的
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class SafeSingleton2 {

    private final static SafeSingleton2 INSTANCE;

    static {
        INSTANCE = new SafeSingleton2();
    }

    private SafeSingleton2() {}

    public static SafeSingleton2 getInstance() {
        return INSTANCE;
    }
}
