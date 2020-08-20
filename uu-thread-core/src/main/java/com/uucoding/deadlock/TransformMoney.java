package com.uucoding.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * 实战转账：
 * 需要两把锁
 * 获取两把锁成功，且余额大于0，则扣除转出人，增加收款人的余额，这是原子操作
 *
 * 顺序相反，导致死锁
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/18  18:50
 */
public class TransformMoney implements Runnable{
    int num;
    static Account a = new Account("a账户", 1000);
    static Account b = new Account("b账户", 1000);
    @Override
    public void run() {

        // a 转钱给b
        if (num == 1) {
            transform(a, b, 100);
        }
        // b 转钱给a
        if (num == 2) {
            transform(b, a, 200);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TransformMoney transformMoneyA = new TransformMoney();
        transformMoneyA.num = 1;
        TransformMoney transformMoneyB = new TransformMoney();
        transformMoneyB.num = 2;
        Thread threadA = new Thread(transformMoneyA);
        Thread threadB = new Thread(transformMoneyB);
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        System.out.println(a.name + " 余额为：" + a.money);
        System.out.println(b.name + " 余额为：" + b.money);
    }

    static class Account {
        // 初始化账户金额
        int money;
        // 账户名称
        String name;

        public Account(String name, int money) {
            this.name = name;
            this.money = money;
        }
    }

    public static void transform(Account from, Account to, int money) {
        synchronized (from) {
            // 如果此处休眠则会产生必然死锁
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (to) {
                if (from.money - money < 0) {
                    System.out.println("账户["+ from.name +"]余额不足");
                } else {
                    from.money -= money;
                    to.money += money;
                    System.out.println("成功转账" + money + "元");
                }
            }
        }
    }
}
