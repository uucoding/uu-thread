package com.uucoding.core.threadattribute;

/**
 * 线程名称
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/27  09:20
 */
public class Name {

    public static void main(String[] args) {
        new Thread().setDaemon(true);
        System.out.println(Thread.currentThread().getName());
    }
}
