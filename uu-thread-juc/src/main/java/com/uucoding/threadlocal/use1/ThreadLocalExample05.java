package com.uucoding.threadlocal.use1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * threadlocal案例：1000线程打印日期，采用线程池创建线程
 *
 * 利用ThreadLocal给每个线程分配一个SimpleDateFormat对象，既保证了线程安全，也高效利用内存
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/24  21:19
 */
public class ThreadLocalExample05 {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    // 给线程池的每个线程都创建一个SimpleDateFormat对象
    ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
// 方法二
    //    ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
//        @Override
//        protected SimpleDateFormat initialValue() {
//            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        }
//    };

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            Runnable task = () -> {
                String dateFormat = new ThreadLocalExample05().getDateFormat(finalI + 1);
                System.out.println(dateFormat);
            };
            executorService.submit(task);
        }
        executorService.shutdown();
    }

    /**
     * 获取秒的日期
     * @param second
     * @return
     */
    public String getDateFormat(int second) {
        // 起始时间是： 1970-01-01 00:00:00 GMT，东八区（北京时间）的初始值是1970-01-01 08:00:00
        Date date = new Date(1000 * second);
        SimpleDateFormat simpleDateFormat = threadLocal.get();
        return simpleDateFormat.format(date);
    }
}
