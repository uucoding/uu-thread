package com.uucoding.deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.uucoding.deadlock.TransformMoney.Account;

/**
 * 多人同时转账，会降低相互交换锁顺序的概率，但是依然会发生死锁，墨菲定律（存在发生的概率，那么就会一定发生）
 *
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/18  19:18
 */
public class MultiTransformMoney {

    // 账户数目
    public final static int NUM_ACCOUNT = 500;
    // 账户金额
    public final static int NUM_MONEY = 1000;
    // 转账次数
    public final static int NUM_COUNT = 10000;
    // 转账线程数
    public final static int NUM_THREAD = 20;
    public static void main(String[] args) {
        Random random = new Random();
        List<Account> accountList = new ArrayList<>();
        // 初始化账户
        for (int i = 0; i < NUM_ACCOUNT; i++) {
            accountList.add(new Account("账户" + i, NUM_MONEY));
        }

        /**
         * 转账线程
         */
        class TransformThread extends Thread {
            @Override
            public void run() {
                // 模拟多次转账
                for (int i = 0; i < NUM_COUNT; i++) {
                    Account fromAccount = accountList.get(random.nextInt(NUM_ACCOUNT));
                    Account toAccount = accountList.get(random.nextInt(NUM_ACCOUNT));
                    int money = random.nextInt(NUM_MONEY);
                    TransformMoney.transform(fromAccount, toAccount, money);
                }
                System.out.println("运行结束");
            }
        }

        // 启动多个线程进行转账
        for (int i = 0; i < NUM_THREAD; i++) {
            new TransformThread().start();
        }
    }
}
