package com.uucoding.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/1  00:15
 */
public class AtomicIntegerFieldUpdaterExample implements Runnable{
    // 标记升级的类 和 升级的字段
    AtomicIntegerFieldUpdater<Candidate> atomicIntegerFieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    // 声明两个候选人
    static Candidate candidateA = new Candidate();
    static Candidate candidateB = new Candidate();

    public static void main(String[] args) throws InterruptedException{
        AtomicIntegerFieldUpdaterExample atomicIntegerFieldUpdaterExample = new AtomicIntegerFieldUpdaterExample();

        Thread threadA = new Thread(atomicIntegerFieldUpdaterExample);
        Thread threadB = new Thread(atomicIntegerFieldUpdaterExample);

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
        System.out.println("未升级的值：" + candidateA.score);
        System.out.println("升级原子变量后的值：" + candidateB.score);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            candidateA.score++;
            // candidateB采用升级方案
            atomicIntegerFieldUpdater.getAndIncrement(candidateB);
        }
    }

    static class Candidate {
        volatile int score;
    }
}
