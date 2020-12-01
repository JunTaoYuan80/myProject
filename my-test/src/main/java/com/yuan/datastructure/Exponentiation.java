package com.yuan.datastructure;

import java.util.Vector;

/**
 * 模幂运算
 * <p>如何处理Mod运算
 * 1.公式：(a * b) % k = (a % k)(b % k) % k
 * <p>
 * 2.幂数的奇偶性
 * a^b = a * a^(b-1)    //奇数
 * a^b = (a^(b/2))^2    //偶数
 *
 * </p>
 *
 * @Author yuanjt
 * @Date 2020-12-01 09:57
 **/
public class Exponentiation {

    static int base = 1337;

    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>();
        v.add(5);
        v.add(9);
        v.add(3);
        v.add(4);

        System.out.println(superPow(3, v));
    }

    /**
     * 递归
     * 幂数的奇偶性
     *
     * @param a
     * @param k
     * @return
     */
    static int mypow(int a, int k) {
        if (k == 0) {
            return 1;
        }
        //奇数
        if (k % 2 == 1) {
            return (a * mypow(a, k - 1)) % base;
        } else {
            int sub = mypow(a, k / 2);
            return (sub * sub) % base;
        }
    }

    /**
     * 非递归
     * <p>
     * 公式：(a * b) % k = (a % k)(b % k) % k
     * </p>
     * @param a
     * @param k
     * @return
     */
    static int mypow1(int a, int k) {
        if (k == 0) {
            return 1;
        }
        a %= base;
        int res = 1;
        for (int i = 0; i < k; i++) {
            res *= a;
            res %= base;
        }
        return res;
    }

    static int superPow(int a, Vector<Integer> v) {
        if (v.isEmpty()) {
            return 1;
        }

        int last = v.lastElement();
        v.remove(v.size() - 1);
        int p1 = mypow(a, last);
        int p2 = mypow(superPow(a, v), 10);

        //每次乘法都得取模
        return (p1 * p2) % base;
    }
}
