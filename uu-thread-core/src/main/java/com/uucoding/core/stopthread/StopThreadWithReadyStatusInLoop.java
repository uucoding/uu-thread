package com.uucoding.core.stopthread;

/**
 * 线程在**每次**迭代(循环)后都阻塞：循环的每次执行都有sleep
 *
 * 可以省略Thread.currentThread().isInterrupted()的检查（前提是try...catch需要包裹循环）
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  00:02
 */
public class StopThreadWithReadyStatusInLoop implements Runnable{

    /**
     * 执行任务的循环里面包含Thread.sleep()方法 ，有就绪状态
     */
    @Override
    public void run() {
        int num = 0;
        try {
            // !Thread.currentThread().isInterrupted()检测是否中断的判断可以去掉，try..catch是将整个循环都包裹在其中，任何一次出现异常，都会终止
            while (num < 1000) {
                System.out.println("输出num:" + (++num));
                // 当有线程中有Thread.sleep方法的时候，线程会进入就绪状态，当接收到中断指令的时候会抛出InterruptedException异常，从而中断线程
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadWithReadyStatusInLoop());
        thread.start();
        ////延迟1ms后 发送终止线程命令
        Thread.sleep(6000);
        // 发送终止命令
        thread.interrupt();
    }
}
