package com.uucoding.core.stopthread;

/**
 * 如何正确停止线程：普通情况的线程，即任务没有阻塞（Thread.sleep()）
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/19  18:57
 */
public class StopThreadWithCommon implements Runnable{

    /**
     * 执行输出10000的倍数
     */
    @Override
    public void run() {
        int num = 1;
        int count = 0;
        // 通过 Thread.currentThread().isInterrupted() 不停检测线程是否被中断了
        while (!Thread.currentThread().isInterrupted() && num < Integer.MAX_VALUE / 2) {
            if (num % 10000 == 0) {
                System.out.println(num + "是10000的倍数");
                count++;
            }
            num++;
        }
        System.out.println("执行完毕！1 - " + Integer.MAX_VALUE / 2 + "之间共计 " + count + " 个 10000 的倍数");
    }

    public static void main(String[] args) throws Exception{
        Thread thread = new Thread(new StopThreadWithCommon());
        thread.start();
        //延迟两秒后 发送终止线程命令
        Thread.sleep(2000);
        //向线程发送终止命令，如果该线程没有设置有关响应中断信号的代码段，那么即便是发送终止命令，该线程不理会的。
        thread.interrupt();
    }
}
