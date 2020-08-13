package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 懒汉式 ：线程不安全的
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class UnSafeSingleton1 {

    private static UnSafeSingleton1 INSTANCE;

    private UnSafeSingleton1() {}

    /**
     * 多线程的情况下这里是不安全的
     * @return
     */
    public static UnSafeSingleton1 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UnSafeSingleton1();
        }
        return INSTANCE;
    }
}
