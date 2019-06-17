package com.yuan.my_project.mytest.softtrap;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://mp.weixin.qq.com/s?__biz=MzIzOTU0NTQ0MA==&mid=2247490475&idx=1&sn=bee5d94253c119d49c156aba6aab4a23&chksm=e92926a4de5eafb2415e6db2db1185da23511eb9494354342f70245ad75f4b20a8538d2a0c09&mpshare=1&scene=1&srcid=&key=49cd1e59990c3d1f29e036a09830f0b433d2707c7e153dda2941fc0956b1a3be01250b55a1f3dfd1cd0ae149c34fe148ac4ae7ddd054e7fe930b5389a28b31a5a24e2eba890395d8fbf88f17655cd335&ascene=1&uin=MTU4NDM4OTc4MA%3D%3D&devicetype=Windows+10&version=62060833&lang=zh_CN&pass_ticket=Rbl2R6Q3BUlXbd72wzYqBD%2BO1UmM0%2BjyCyCR6qKEftEYvMZIkci2x8tM1WN2uVWh
 *
 * @author yuanjuntao
 * @date 2019/6/17 13:34
 */
public class Test {
    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        floatPrimitiveTest();
        floatWrapperTest();
        switchTest();
        bigdecimalTest();
        lockTest();
    }

    public static void floatPrimitiveTest() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        if (a == b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static void floatWrapperTest() {
        Float a = Float.valueOf(1.0f - 0.9f);
        Float b = Float.valueOf(0.9f - 0.8f);
        if (a.equals(b)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static void switchTest() {
        try {
            String param = null;
            switch (param) {
                case "null":
                    System.out.println("null");
                    break;
                default:
                    System.out.println("default");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bigdecimalTest() {
        BigDecimal a = new BigDecimal(0.1);
        System.out.println(a);
        BigDecimal b = new BigDecimal("0.1");
        System.out.println(b);
    }

    public static void lockTest() {
        try {
            lock.tryLock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
