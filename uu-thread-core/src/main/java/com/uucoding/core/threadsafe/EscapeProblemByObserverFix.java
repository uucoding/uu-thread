package com.uucoding.core.threadsafe;

/**
 * 逸出问题之：观察者模式中注册监听器
 *
 * 使用工厂方式进行修复
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/28  22:34
 */
public class EscapeProblemByObserverFix {
    int count;
    EventListener eventListener;
    private EscapeProblemByObserverFix() {
        // 新建一个监听器
        eventListener = (e) -> {
            System.out.println("\n我被注册了一个数字: " + count);
        };
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        this.count = 100;
    }

    public static EscapeProblemByObserverFix getInstance(MySource mySource) {
        EscapeProblemByObserverFix escapeProblemByObserverFix = new EscapeProblemByObserverFix();

        mySource.registerListener(escapeProblemByObserverFix.eventListener);
        return escapeProblemByObserverFix;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();
        EscapeProblemByObserverFix.getInstance(mySource);
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 10ms之后传入一个事件事件
            mySource.eventCome(new Event() {});
        }).start();
    }

    static class MySource {
        private EventListener listener;

        void registerListener(EventListener listener) {
            this.listener = listener;
        }

        /**
         * 事件来的时候执行这里
         * @param e
         */
        void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("\n事件还未初始化完毕");
            }

        }
    }

    /**
     * 事件监听器
     */
    interface EventListener {
        /**
         * 监听执行事件
         *
         * @param e
         */
        void onEvent(Event e);
    }

    /**
     * 事件
     */
    interface Event {
    }
}
