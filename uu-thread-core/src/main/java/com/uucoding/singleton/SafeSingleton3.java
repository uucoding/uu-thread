package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 懒汉式 （静态内部类） 线程安全的 可用的
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class SafeSingleton3 {

    private SafeSingleton3() {}

    /**
     * 加载的时候，JVM不会初始化这类，只有当有线程第一次调用{@link SafeSingleton3#getInstance()}才会初始化一次
     */
    private static class InnerSingle {
        private final static SafeSingleton3 INSTANCE = new SafeSingleton3();
    }

    public static SafeSingleton3 getInstance() {
        return InnerSingle.INSTANCE;
    }
}
