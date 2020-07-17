package com.uucoding.basic.thread;

/**
 * 观察线程在系统活动监视器的状态
 *
 * 操作：启动后观察监视器中Java进程是否增加，以及线程数量
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/17  11:25
 */
public class ObserveThread {

    public static void main(String[] args){
        //创建二百个线程，观察活动监视器中的变化
        for (int i = 0; i < 200; i++) {
            new Thread(new Runnable() {
                public void run(){
                    try {
                        //休眠10s 观察线程变化
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
