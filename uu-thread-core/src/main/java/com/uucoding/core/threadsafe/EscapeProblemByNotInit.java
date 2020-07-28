package com.uucoding.core.threadsafe;

import java.util.concurrent.TimeUnit;

/**
 * 逸出问题之：
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/28  22:34
 */
public class EscapeProblemByNotInit {
    static Point point;

    static class Point {
        final int x, y;

        public Point(int x, int y) {
            this.x = x;
            // 还未初始化，就设置值
            EscapeProblemByNotInit.point = this;
            // 做其他相关事情
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 初始化
        new Thread(() -> {
            new Point(1, 2);
        }).start();
        // 由于还未初始化完毕，就会导致得到的对象的内容可能是不一致的
        Thread.sleep(100);
        System.out.println(point); // Point{x=1, y=0}

        Thread.sleep(1000);
        System.out.println(point); //Point{x=1, y=2}

    }
}
