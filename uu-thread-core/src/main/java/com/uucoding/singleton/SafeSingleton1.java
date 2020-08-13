package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 饿汉式 （静态变量） 线程安全的
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class SafeSingleton1 {

    private final static SafeSingleton1 INSTANCE = new SafeSingleton1();

    private SafeSingleton1() {}

    public static SafeSingleton1 getInstance() {
        return INSTANCE;
    }
}
