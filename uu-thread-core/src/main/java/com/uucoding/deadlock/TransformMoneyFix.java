package com.uucoding.deadlock;

/**
 * 实战转账：修复死锁问题
 * 通过调整顺序，让转账的时候不会出现获取锁的顺序相反问题，可以使用对象hashcode比较大小，让他们拥有相同的获取顺序
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/18  18:50
 */
public class TransformMoneyFix implements Runnable{
    int num;
    static Account a = new Account("a账户", 1000);
    static Account b = new Account("b账户", 1000);
    static Object locked = new Object();
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
        TransformMoneyFix transformMoneyA = new TransformMoneyFix();
        transformMoneyA.num = 1;
        TransformMoneyFix transformMoneyB = new TransformMoneyFix();
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
        class Helper {
            // 抽取方法
            public void transform() {
                if (from.money - money < 0) {
                    System.out.println("账户["+ from.name +"]余额不足");
                } else {
                    from.money -= money;
                    to.money += money;
                    System.out.println(from.name + " 成功转账" + money + "元");
                }
            }
        }
        // 获取对象的 hashCode
        int fromHashCode = System.identityHashCode(from);
        int toHashCode = System.identityHashCode(to);
        // 依据hashcode进行锁的顺序排队
        if (fromHashCode > toHashCode) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transform();
                }
            }
        } else if (fromHashCode < toHashCode) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transform();
                }
            }
        } else {
            // 如果hashcode相等的情况下，那就额外加一个锁进行拦截处理
            synchronized (locked) {
                synchronized (from) {
                    synchronized (to) {
                        new Helper().transform();
                    }
                }
            }
        }

    }
}
