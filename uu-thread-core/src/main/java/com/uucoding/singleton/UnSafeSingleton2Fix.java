package com.uucoding.singleton;

/**
 * 单例模式
 *
 * 懒汉式-双重检查 ：线程安全的，但是可能会产生重排序，引发异常
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class UnSafeSingleton2Fix {

    private static UnSafeSingleton2Fix INSTANCE;

    private UnSafeSingleton2Fix() {}

    /**
     * 锁内 锁外 双重检查，线程安全，但是可能会产生重排序，引发异常
     * @return
     */
    public static UnSafeSingleton2Fix getInstance() {
        if (INSTANCE == null) {
            synchronized (UnSafeSingleton2Fix.class) {
                // 第二次检查，避免多线程多次创建
                if (INSTANCE == null) {
                    // new 对象的时候实际上经历了三步
                    // 1、 new 一个空对象
                    // 2、 调用类的构造方法，给空对象初始化相关值
                    // 3、 将对象赋值给变量引用
                    // 如果2、3两步发生重排序，那么可能会导致，其他线程得到的是个空对象（相关值未初始化）

                    INSTANCE = new UnSafeSingleton2Fix();
                }
            }
        }
        return INSTANCE;
    }
}
