package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 懒汉式-双重检查-volatile ：线程安全的 ，防止重排序（可用，推荐使用）
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class UnSafeSingleton2FixFix {

    private volatile static UnSafeSingleton2FixFix INSTANCE;

    private UnSafeSingleton2FixFix() {}

    /**
     * 锁内 锁外 双重检查，线程安全
     * @return
     */
    public static UnSafeSingleton2FixFix getInstance() {
        if (INSTANCE == null) {
            synchronized (UnSafeSingleton2FixFix.class) {
                // 第二次检查，避免多线程多次创建
                if (INSTANCE == null) {
                    INSTANCE = new UnSafeSingleton2FixFix();
                }
            }
        }
        return INSTANCE;
    }
}
