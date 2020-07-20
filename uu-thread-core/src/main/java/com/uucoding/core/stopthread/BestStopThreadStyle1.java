package com.uucoding.core.stopthread;

/**
 * 最优停止方式一：传递中断
 * 封装的方法供外部线程调用，不要自己catch异常，而是抛出异常(此时会强制在run方法中处理)，否则会导致中断恢复，即interrupt = false
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  16:06
 */
public class BestStopThreadStyle1 implements Runnable {


    @Override
    public void run() {
        while (true && !Thread.currentThread().isInterrupted()) {
            System.out.println("执行子任务");
            try {
                subTask();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

        }
    }

    /**
     * 子任务， 将中断异常InterruptedException传递给顶层方法run，不能在自己的方法中进行try...catch
     *
     * @throws InterruptedException
     */
    private void subTask() throws InterruptedException {
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new BestStopThreadStyle1());

        thread.start();

        Thread.sleep(3000);

        thread.interrupt();
    }
}
