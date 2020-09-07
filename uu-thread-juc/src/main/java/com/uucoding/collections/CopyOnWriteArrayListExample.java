package com.uucoding.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 对比CopyOnWriteArrayList 和 ArrayList
 *
 * CopyOnWriteArrayList在迭代的时候可以修改
 *
 * @author : uu
 * @version : v1.0
 * @Date 2020/9/3  23:23
 */
public class CopyOnWriteArrayListExample {

    public static void main(String[] args) {
        System.out.println("测试CopyOnWriteArrayList");
        CopyOnWriteArrayList<String> list1 = new CopyOnWriteArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        Iterator<String> iterator1 = list1.iterator();
        while (iterator1.hasNext()) {
            System.out.println("CopyOnWriteArrayList :" + list1);
            String next = iterator1.next();
            System.out.println(next);
            if (next.equals("2")) {
                list1.remove("2");
            }
            if (next.equals("3")) {
                list1.add("5");
            }
        }
        for (String s : list1) {
            System.out.println("CopyOnWriteArrayList2 for :" + list1);
            System.out.println(s);
        }

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        Iterator<String> iterator2 = list2.iterator();
        System.out.println("\n测试ArrayList");
        while (iterator2.hasNext()) {
            System.out.println("arrayList :" + list2);
            String next = iterator2.next();
            System.out.println(next);
            if (next.equals("2")) {
                list2.remove("2");
            }
            if (next.equals("3")) {
                list2.add("5");
            }
        }

    }
}
