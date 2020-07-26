package com.uucoding.core.objectandthreadmethod;

/**
 * 案例：主线程调用join时候的状态 ->WAITING
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/26  16:17
 */
public class ThreadJoinState {

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Runnable runnable = () -> {
            try {
                Thread.sleep(3000);
                System.out.println("主函数的状态为：" + mainThread.getState());
                System.out.println(Thread.currentThread().getName() + "执行完毕！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        Thread threadA = new Thread(runnable);
        System.out.println("开始启动子线程");
        threadA.start();
        // 等待 A线程执行完毕
        threadA.join();
        // 等价于
//        synchronized (threadA) {
//            threadA.wait();
//        }
        System.out.println("子线程执行完毕");
    }
}
