package com.uucoding.core.statusthread;

/**
 * 演示三种状态转化：new -> runnable -> terminated
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/21  14:29
 */
public class NewRunnableTerminatedStatus implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    /**
     * 运行查看结果
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new NewRunnableTerminatedStatus());
        //输出线程还未启动的状态：
        System.out.println("线程还未启动的状态：" + thread.getState()); // NEW
        thread.start();
        System.out.println("线程启动后的状态：" + thread.getState()); // RUNNABLE
        // 休眠 5ms，此时线程正在执行任务，检测状态
        Thread.sleep(5);
        System.out.println("线程运行中的状态：" + thread.getState()); // RUNNABLE
        // 休眠 100ms， 此时线程应该已经把任务运行完毕
        Thread.sleep(100);
        System.out.println("线程运行完任务的状态：" + thread.getState()); // TERMINATED

    }
}
