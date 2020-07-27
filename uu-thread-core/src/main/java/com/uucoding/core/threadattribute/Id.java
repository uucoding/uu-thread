package com.uucoding.core.threadattribute;

/**
 *  id 为递增，main线程id为1,新建线程id不为2（因为jvm会主动创建一部分线程）
 * {@link Thread#getId()}
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/26  23:04
 */
public class Id {

    public static void main(String[] args) {
        System.out.println("主线程的id为：" + Thread.currentThread().getId());
        System.out.println("新建线程的id为：" + new Thread().getId());
    }
}
