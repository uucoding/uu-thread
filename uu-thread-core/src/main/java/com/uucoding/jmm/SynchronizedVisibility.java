package com.uucoding.jmm;

/**
 * synchronized可见性案例文字描述
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/2  22:40
 */
public class SynchronizedVisibility {

    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;

    public void write() {
        a = 2;
        b = 3;
        c = 4;
        // 根据hb原则，同一线程之前的操作都会被之后看到，当此处拿到锁之后，那么abc已经赋值完毕
        synchronized (this) {
            d = 5;
        }
    }

    public void read() {
        // 根据hb原则，线程加锁能看到另一个线程解锁之前的所有操作，即当read获取锁之后，write释放锁之前的操作对read都是可见的
        // 即后续的赋值操作也是正确读取的
        synchronized (this) {
            int aa = a;
        }
        int bb = b;
        int cc = c;
        int dd = d;
    }
}
