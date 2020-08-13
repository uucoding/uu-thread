package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 懒汉式 ：线程安全的，使用同步方法 （不推荐用） 因为效率低
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class UnSafeSingleton1Fix {

    private static UnSafeSingleton1Fix INSTANCE;

    private UnSafeSingleton1Fix() {}

    /**
     * 每次只会有一个线程访问，其他线程等待，线程安全，但是效率低
     * @return
     */
    public static synchronized UnSafeSingleton1Fix getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UnSafeSingleton1Fix();
        }
        return INSTANCE;
    }
}
