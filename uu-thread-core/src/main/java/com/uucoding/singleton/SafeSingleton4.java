package com.uucoding.singleton;

/**
 * 单例模式
 * <p>
 * 枚举类 （线程安全的） 可用的， 推荐生产使用
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/13  21:43
 */
public class SafeSingleton4 {

    private enum InnerEnum {
        INSTANCE;

        private SafeSingleton4 instance;

        InnerEnum() {
            this.instance = new SafeSingleton4();
        }
    }

    private SafeSingleton4() {
    }

    public static SafeSingleton4 getInstance() {
        return InnerEnum.INSTANCE.instance;
    }
}
