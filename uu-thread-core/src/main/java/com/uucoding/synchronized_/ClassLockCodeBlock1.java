package com.uucoding.synchronized_;

/**
 * 演示类锁：.class锁
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/22  19:53
 */
public class ClassLockCodeBlock1 implements Runnable {
    @Override
    public void run() {
        operate();
    }

    public void operate() {
        // 类锁 代码块
        synchronized (ClassLockCodeBlock1.class) {
            System.out.println(Thread.currentThread().getName() + " 获取到了锁");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 释放锁");
        }
    }

    public static void main(String[] args) {
        // 创建两个不同的实例，验证共享一把锁
        ClassLockCodeBlock1 classLockCodeBlock1 = new ClassLockCodeBlock1();
        ClassLockCodeBlock1 classLockCodeBlock11 = new ClassLockCodeBlock1();

        Thread threadA = new Thread(classLockCodeBlock1);
        Thread threadB = new Thread(classLockCodeBlock11);

        threadA.start();
        threadB.start();

        while (threadA.isAlive() || threadB.isAlive()) {
        }

        System.out.println("所有线程执行完毕");
    }
}
