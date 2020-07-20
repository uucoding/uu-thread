package com.uucoding.core.stopthread.error;

/**
 * 错误的终止方式：使用已经过期的方法stop
 * 会导致线程运行到一半突然停止,没有办法完成一个基本的单位操作(做一道菜需要放的佐料)， 会造成脏数据（有的菜佐料放不全）
 * 有一种错误的理论：不实用stop是因为stop不能释放monitor的锁，会造成程序卡死
 * 至于stop为什么会被弃用，是因为stop本质上是不安全的，但是停止线程会解锁所有的monitor
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/20  20:41
 */
public class ErrStopThread implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //正确方式修改：第二步 添加中断校验
//            if (Thread.currentThread().isInterrupted()) {
//                System.out.println("终止做菜");
//                break;
//            }
            System.out.println("开始做第 "+ i +" 道菜");
            for (int j = 0; j < 10; j++) {
                System.out.println("加入第" + j +"种佐料！");
                try {
                    // 每加入一次佐料就休眠100ms
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // 正确方式修改：第三步 恢复中断
                    // Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            System.out.println("结束做第 "+ i +" 道菜");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ErrStopThread());
        thread.start();
        try {
            // 一秒后执行stop方法，输出结果可以发现有的菜佐料是没放完的
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.stop();
        // 正确修改第一步：使用中断发送指令
//        thread.interrupt();

    }
}
