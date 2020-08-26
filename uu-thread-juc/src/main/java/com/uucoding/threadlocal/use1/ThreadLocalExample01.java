package com.uucoding.threadlocal.use1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * threadlocal案例：10线程打印日期，采用for循环创建线程
 *
 * 进阶查看代码{@link ThreadLocalExample02}
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/24  21:19
 */
public class ThreadLocalExample01 {


    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                String dateFormat = new ThreadLocalExample01().getDateFormat(finalI + 1);
                System.out.println(dateFormat);
            }).start();
        }
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
