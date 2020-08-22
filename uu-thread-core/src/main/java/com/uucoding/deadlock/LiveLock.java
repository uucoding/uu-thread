package com.uucoding.deadlock;

/**
 * 活锁演示
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/8/21  19:11
 */
public class LiveLock {

    public static void main(String[] args) {
        Person a = new Person("A");
        Person b = new Person("B");

        Spoon spoon = new Spoon(a);

        new Thread(() -> {
            a.eat(spoon, b);
        }).start();

        new Thread(() -> {
            b.eat(spoon, a);
        }).start();
    }

    /**
     * 共享资源 勺子
     */
    static class Spoon {
        // 勺子的拥有者
        private Person owner;

        public Spoon(Person owner) {
            this.owner = owner;
        }

        /**
         * 谁拿到勺子谁就可以吃饭
         */
        public synchronized void use() {
            System.out.println(owner.getName() + " 准备吃饭！");
        }

        public Person getOwner() {
            return owner;
        }

        public void setOwner(Person owner) {
            this.owner = owner;
        }
    }

    /**
     * 分配共享资源：人
     */
    static class Person {

        // 人的名字
        private String name;

        // 是否需要勺子
        private boolean need;

        public void eat(Spoon spoon, Person other) {
            // 只有当前这个人需要，才会执行这里
            while (this.need) {
                // 想要吃饭，只能先拥有勺子，先判断是否勺子的所属是自己
                if (spoon.getOwner() != this) {
                    // 如果不是自己，那么可能是对方正在使用，稍微等一下
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                // 如果对方需要，那就得先给对方
                if (other.isNeed()) {
                    System.out.println(this.name + " 让出勺子给" + other.getName());
                    spoon.setOwner(other);
                    continue;
                }
                // 当勺子在我手里，且对方不需要，才允许我使用
                spoon.use();
                this.need = false;
                System.out.println(this.name + " 使用完勺子，并让出！");
                spoon.setOwner(other);
            }
        }

        public Person(String name) {
            this.name = name;
            this.need = true;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isNeed() {
            return need;
        }

        public void setNeed(boolean need) {
            this.need = need;
        }
    }
}
