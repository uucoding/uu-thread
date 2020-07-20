package com.uucoding.core.stopthread.error.volatilestyle;

/**
 * 通过 volatile关键字修饰boolean属性 进行中断线程
 * 演示volatile的局限性，此种方式可以中断
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  21:13
 */
public class CanStopThreadByVolatile implements Runnable {

    // volatile修饰让该属性在内存模型中具有可见性
    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            while (num < 1000 && !canceled) {
                System.out.println("第 " + num + " 输出");
                Thread.sleep(50);
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CanStopThreadByVolatile canStopThreadByVolatile = new CanStopThreadByVolatile();
        Thread thread = new Thread(canStopThreadByVolatile);
        thread.start();
        // 延迟一秒设置取消
        Thread.sleep(1000);
        canStopThreadByVolatile.canceled = true;
    }
}
