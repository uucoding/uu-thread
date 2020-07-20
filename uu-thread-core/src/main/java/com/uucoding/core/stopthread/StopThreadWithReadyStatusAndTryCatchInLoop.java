package com.uucoding.core.stopthread;

/**
 * 执行任务的循环里面包含Thread.sleep()方法 ，有就绪状态，且try...catch放在循环内部，会导致中断线程失效
 *
 * 原因：java关于sleep的机制是：一旦接收中断命令，响应后，就会将interrupt标记位清除，即Thread.currentThread().isInterrupted()又恢复成原来的值（false）
 *      所以无法正确检测
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  00:12
 */
public class StopThreadWithReadyStatusAndTryCatchInLoop  implements Runnable{

    /**
     * 执行任务的循环里面包含Thread.sleep()方法 ，有就绪状态
     */
    @Override
    public void run() {
        int num = 0;
        while (num < 1000 && !Thread.currentThread().isInterrupted()) {
            System.out.println("输出num:" + (++num));
            try {
                // 当有线程中有Thread.sleep方法的时候，线程会进入就绪状态，当接收到中断指令的时候会抛出InterruptedException异常
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                // java关于sleep的机制是：一旦接收中断命令，响应后，就会将interrupt标记位清除，即Thread.currentThread().isInterrupted()又恢复成原来的值（false）
                // 异常后需要手动再次中断一次
                Thread.currentThread().interrupt();
                e.printStackTrace();

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadWithReadyStatusAndTryCatchInLoop());
        thread.start();
        ////延迟5s后 发送终止线程命令
        Thread.sleep(5000);
        // 发送终止命令
        thread.interrupt();
    }
}