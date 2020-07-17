package com.uucoding.basic.thread;

/**
 * debugger 观察JVM自动创建的线程
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/17  12:15
 */
public class DebuggerJavaThread {

    public static void main(String[] args) {
        // 此处设置断点，查看Debugger下的Threads选项卡，即可看到JVM自动创建的线程
        System.out.println("hello");
    }
}
