package com.uucoding.core.threadsafe;

import java.util.HashMap;
import java.util.Map;

/**
 * 逸出问题之：方法返回一个private对象（private的本意是不让外部访问）
 * @author : uu
 * @version : v1.0
 * @Date 2020/7/28  22:34
 */
public class EscapeProblemByPublish {

    /**
     * 当前属性本意不不想让外部直接修改，只是提供一个返回值，如果多线程中有一个修改了这个数据，就会导致其他线程拿到的是错误的
     */
    private Map<String, String> state;

    public EscapeProblemByPublish() {
        this.state = new HashMap<>();
        state.put("1", "周一");
        state.put("2", "周二");
        state.put("3", "周三");
    }

    public Map<String, String> getState() {
        return state;
    }

    public static void main(String[] args) {
        EscapeProblemByPublish escapeProblemByPublish = new EscapeProblemByPublish();
        Map<String, String> state = escapeProblemByPublish.getState();

        System.out.println(state.get("1")); //  周一
        // 删除操作
        state.remove("1");

        state = escapeProblemByPublish.getState();
        System.out.println(state.get("1")); // null
    }
}
