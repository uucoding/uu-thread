package com.uucoding.threadlocal.use1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * threadlocal案例：1000线程打印日期，采用线程池创建线程
 *
 * 每个线程都会创建一个SimpleDateFormat对象，造成资源浪费
 *
 * 进阶查看代码{@link ThreadLocalExample03}
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/24  21:19
 */
public class ThreadLocalExample02 {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            Runnable task = () -> {
                String dateFormat = new ThreadLocalExample02().getDateFormat(finalI + 1);
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // 起始时间是： 1970-01-01 00:00:00 GMT，东八区（北京时间）的初始值是1970-01-01 08:00:00
        return simpleDateFormat.format(new Date(1000 * second));
    }
}
