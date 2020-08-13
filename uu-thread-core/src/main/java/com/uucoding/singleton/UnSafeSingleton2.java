package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 懒汉式 ：线程不安全的 （同步代码块处理，不可用）
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class UnSafeSingleton2 {

    private static UnSafeSingleton2 INSTANCE;

    private UnSafeSingleton2() {}

    /**
     * 通过只锁创建的部分，也是线程不安全的
     * @return
     */
    public static UnSafeSingleton2 getInstance() {
        if (INSTANCE == null) {
            // 可能多个线程会同时进入这一行，也会导致多次创建
            synchronized (UnSafeSingleton2.class) {
                INSTANCE = new UnSafeSingleton2();
            }
        }
        return INSTANCE;
    }
}
