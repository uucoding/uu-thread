package com.uucoding.synchronized_;

/**
 * 演示类锁：修饰静态方法的锁
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/22  19:53
 */
public class ClassLockStaticMethod implements Runnable {
    @Override
    public void run() {
        operate();
    }

    /**
     * 同步静态方法锁
     */
    public synchronized static void operate() {
        System.out.println(Thread.currentThread().getName() + " 获取到了锁");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 释放锁");
    }

    public static void main(String[] args) {
        // 创建两个不同的实例，验证共享一把锁
        ClassLockStaticMethod objectLockMethodA = new ClassLockStaticMethod();
        ClassLockStaticMethod objectLockMethodB = new ClassLockStaticMethod();

        Thread threadA = new Thread(objectLockMethodA);
        Thread threadB = new Thread(objectLockMethodB);

        threadA.start();
        threadB.start();

        while (threadA.isAlive() || threadB.isAlive()) {
        }

        System.out.println("所有线程执行完毕");
    }
}
