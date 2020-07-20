package com.uucoding.core.stopthread;

/**
 * 停止的线程包含就绪状态：有sleep()方法 或者wait等就绪调用
 *
 * 当线程进入阻塞状态的时候，通过try...catch InterruptedException异常 依然可以响应中断指令
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/19  23:46
 */
public class StopThreadWithReadyStatus implements Runnable {

    /**
     * 执行任务中包含Thread.sleep()方法 ，有就绪状态
     */
    @Override
    public void run() {
        int num = 0;
        try {
            // !Thread.currentThread().isInterrupted() 此处判断需要保留，因为程序是顺序执行的，中断命令发送过来 循环依然正常执行，直到循环结束，进入到下面的就绪状态，才会中断
            while (num < Integer.MAX_VALUE && !Thread.currentThread().isInterrupted()) {
                System.out.println("输出num:" + (++num));
            }
            // 当有线程中有Thread.sleep方法的时候，线程会进入就绪状态，当接收到中断指令的时候会抛出InterruptedException异常，从而中断异常
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadWithReadyStatus());
        thread.start();
        //任务中阻塞1s，此处设置0.5秒，子线程还处于等待状态
        Thread.sleep(500);
        // 发送终止命令
        thread.interrupt();
    }
}
