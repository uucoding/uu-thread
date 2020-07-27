package com.uucoding.core.uncaughtexception;

/**
 * 通过使用UncaughtExceptionHandler的方式可以设置一个全局捕获异常子线程的地方，不过线程的问题应该线程自己本身来解决，而不要委托到外部
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/27  22:44
 */
public class UncaughtExceptionDealChildThreadException {

    /**
     * 全局的异常处理器，这里自定义后，会自动由ThreadGroup调用
     * 默认的处理器 {@link ThreadGroup#uncaughtException(Thread, Throwable)}
     */
    static Thread.UncaughtExceptionHandler myUncaughtExceptionHandler = (thread, e) -> {
        e.printStackTrace();
        System.out.println(Thread.currentThread().getName());
    };

    public static void main(String[] args) {
        // 设置默认全局的处理
        Thread.setDefaultUncaughtExceptionHandler(myUncaughtExceptionHandler);

        Runnable runnable = () -> {
            throw new RuntimeException(Thread.currentThread().getName() + " 发生异常");
        };
        // 两个线程会连续抛出异常，而不是第一个抛出后被捕获
        Thread thread = new Thread(runnable);
        // 这种方式只能单个线程使用
        // thread.setUncaughtExceptionHandler(myUncaughtExceptionHandler);
        thread.start();
        new Thread(runnable).start();
    }
}
