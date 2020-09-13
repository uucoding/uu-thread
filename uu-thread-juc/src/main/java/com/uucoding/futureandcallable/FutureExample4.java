package com.uucoding.futureandcallable;

import java.util.concurrent.*;

/**
 * {@link Future} 和 {@link Callable} 案例4
 * <p>
 * 演示：get方法超时，以及超时后的处理（调用{@link Future#cancel(boolean)}）并演示cancel传入false和true的区别（是否中断正在执行的任务）
 * <p>
 * 模拟请求广告
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/13  18:03
 */
public class FutureExample4 {

    /**
     * 广告
     */
    static class Ad {
        String name;

        public Ad(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Ad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * 抓取广告
     */
    static class FetchAdTask implements Callable<Ad> {

        @Override
        public Ad call() throws Exception {
            try {
                System.out.println("请求中......");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("休眠期间被中断");
                return new Ad("休眠期间被中断：返回中断广告");
            }
            System.out.println("取消是否会执行这里");
            return new Ad("请求成功：获取数据库广告");
        }
    }

    public void printAd() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Ad> future = executorService.submit(new FetchAdTask());
        Ad ad;
        try {
            ad = future.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            ad = new Ad("请求中断：中断广告");
        } catch (ExecutionException e) {
            ad = new Ad("请求异常：异常广告");
        } catch (TimeoutException e) {
            ad = new Ad("请求超时：超时广告");
            // cancel设置true，会发送一个中断命令
//            future.cancel(true);
            // cancel设置false，会执行完当前任务
            future.cancel(false);
        }
        System.out.println("result : " + ad);
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new FutureExample4().printAd();
    }


}
