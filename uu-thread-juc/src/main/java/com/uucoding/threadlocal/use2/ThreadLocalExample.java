package com.uucoding.threadlocal.use2;

/**
 * 演示ThreadLocal用法2
 *
 * 同一线程存储并传递用户
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/24  23:27
 */
public class ThreadLocalExample {

    public static void main(String[] args) {
        // 调用Service1
        new Service1().process();
    }
}

class Service1 {
    public void process() {
        User uu = new User("uu");
        // 存储至当前线程
        ThreadLocalHolder.holder.set(uu);
        ThreadLocalHolder.holder.remove();
        // 调用Service2
        new Service2().process();
    }
}
class Service2 {
    public void process() {
        // 获取当前线程存储的用户信息
        User user = ThreadLocalHolder.holder.get();
        System.out.println("Service2 获取用户：" + user.name);
        // 调用Service3
        new Service3().process();
    }
}
class Service3 {
    public void process() {
        User user = ThreadLocalHolder.holder.get();
        System.out.println("Service3 获取用户：" + user.name);
    }
}

/**
 * 用户类
 */
class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}

/**
 * ThreadLocal工具类
 */
class ThreadLocalHolder {
    static ThreadLocal<User> holder = new ThreadLocal<User>();
}
