package com.uucoding.core.objectandthreadmethod;

/**
 * wait 相关用法
 *
 * 执行顺序，线程1先调用，执行到wait方法后,进入阻塞，并释放锁之后，线程2抢占锁后执行至notify方法，唤醒一个线程（当前只有一个线程1）
 * wait调用的时候会释放锁
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/23  14:24
 */
public class ObjectWait {
    // 创建类锁
    private static Object object = new Object();
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable1 = () -> {
            synchronized (object) {
                System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
                try {
                    //进入等待状态，并释放锁
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() + "再次获取锁并执行");
            }
        };
        Runnable runnable2 = () -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "获取锁");
                object.notifyAll();
                System.out.println(Thread.currentThread().getName() + "调用了notify");
            }
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        // 保证runnable1 先执行，并调用wait方法
        Thread.sleep(20);
        thread2.start();
    }
}
