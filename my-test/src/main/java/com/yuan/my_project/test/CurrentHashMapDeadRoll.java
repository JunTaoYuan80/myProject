package com.yuan.my_project.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AaAa 和 BBBB的hashcode值相同，导致computeIfAbsent方法中的for各种if判断 都没走到，从而没有brank 导致了for死循环
 *
 * @Author yuanjt
 * @Date 2020-12-21 18:17
 **/
public class CurrentHashMapDeadRoll {

    public static void main(String[] args) {

        System.out.println("start.");
        Map<String, Integer> map = new ConcurrentHashMap<>(16);
        map.computeIfAbsent(
                "AaAa",
                key -> {
                    return map.computeIfAbsent(
                            "BBBB",
                            key2 -> 42);
                }
        );

        System.out.println("end.");
    }


}
