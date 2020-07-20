package com.uucoding.core.stopthread;

/**
 * 最优停止方式二：如果不能或者无法抛出异常，想在本类处理，那么就可以采用恢复中断的方式，在自己内部处理
 * 恢复中断：即在catch中调用Thread.currentThread().interrupt()恢复中断
 * 以便后续代码执行中能够发现中断
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  16:06
 */
public class BestStopThreadStyle2 implements Runnable {


    @Override
    public void run() {
        while (true) {
            // 检查线程是否中断
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("中断线程");
                break;
            }
            System.out.println("执行子任务");
            subTask();
        }
    }

    /**
     * 子任务中捕获异常，并恢复中断
     */
    public void subTask(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //恢复中断
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new BestStopThreadStyle2());

        thread.start();

        Thread.sleep(3000);

        thread.interrupt();
    }
}
