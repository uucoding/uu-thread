package com.uucoding.core.objectandthreadmethod;

/**
 * 演示：wait,notify,notifyAll的操作
 * <p>
 * 设置三个线程，线程1，2被阻塞，3唤醒他们，
 * 验证start先启动并不表示先执行
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/25  16:46
 */
public class ObjectWaitNotifyAll implements Runnable {

    private static Object object = new Object();

    @Override
    public void run() {
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            try {
                System.out.println(Thread.currentThread().getName() + " 进入等待状态，释放锁");
                object.wait();
                System.out.println(Thread.currentThread().getName() + " 被重新唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 运行结束");
    }

    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(new ObjectWaitNotifyAll(), "threadA");
        Thread threadB = new Thread(new ObjectWaitNotifyAll(), "threadB");

        Thread threadC = new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " 获取锁，并准备释放锁");
                //notifyAll可以唤醒所有object修饰的阻塞线程
                object.notifyAll();
                // 将notifyAll 换成notify此时只会随机唤醒其中一个线程，另一个线程始终无法执行
//                object.notify();
                // 查看此时线程A和B的状态，会从waiting进入blocked状态：因为notify之后，当前线程不会立刻释放锁，而等待的线程则不能直接进入runnable状态
                System.out.println(threadA.getState());
                try {
                    // 演示打印a和b线程状态
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 通知其他线程，可以获取锁了");

            }
        }, "threadC");
        threadA.start();
        threadB.start();
        // 为了确保 threadA 和 threadB 先执行，让主线程休眠100ms之后再去执行threadC【表示 先start的线程不一定先执行】
        Thread.sleep(100);
        System.out.println(".." + threadA.getState());
        System.out.println(".." + threadB.getState());
        threadC.start();

    }
}
