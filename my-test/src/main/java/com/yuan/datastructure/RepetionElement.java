package com.yuan.datastructure;

import static java.lang.Math.abs;

/**
 * 如何寻找缺失和重复的元素
 * <p>
 * 1.异或运算
 * 2.等差数列
 *
 * @Author yuanjt
 * @Date 2020-12-29 09:46
 **/
public class RepetionElement {

    public static void main(String[] args) {
        int[] a = {0, 2, 4, 3, 1, 6, 5, 8};
        System.out.println(missNumber(a));
        System.out.println(missNumber2(a));

        int[] num = {2, 4, 3, 1, 7, 5, 5, 8};
        System.out.println(dupMiss(num));
    }

    /**
     * a^b^a = b^(a^a)=b^0=b
     * <p>
     * 一个数和它本身做异或运算结果为0， 一个数和0做异或运算还是它本身
     * </p>
     *
     * @param a
     * @return
     */
    static int missNumber(int[] a) {
        if (a == null) {
            return -1;
        }

        int n = a.length;
        int res = 0;
        res ^= n;

        for (int i = 0; i < a.length; i++) {
            res ^= i ^ a[i];
        }

        return res;
    }

    /**
     * 等差数列
     *
     * @param num
     * @return
     */
    static int missNumber2(int[] num) {
        if (num == null) {
            return -1;
        }

        int n = num.length;
        int res = n - 0;
        for (int i = 0; i < num.length; i++) {
            res += i - num[i];
        }

        return res;
    }


    /**
     * 寻找重复且缺失的数
     *
     * 通过将每个索引对应的元素变成负数， 以表示这个索引这个索引被对应过一次，如果重复对应 则为重复元素；再次遍历元素 找出非负元素即为重复元素下标
     * 时间复杂度O(n), 空间复杂度O(1).
     * @param num
     * @return
     */
    static int dupMiss(int[] num) {
        if (num == null) {
            return -1;
        }
        int dup = 0;
        for (int i = 0; i < num.length; i++) {
            int index = abs(num[i]) - 1;
            if (num[index] >= 0) {
                num[index] = -1 * num[index];
            } else {
                dup = abs(num[i]);
            }
        }
        int miss = -1;
        for (int i = 0; i < num.length; i++) {
            if (num[i] > 0) {
                miss = i;
            }
        }


        System.out.println("dup:" + dup + ", miss:" + miss);
        return dup;
    }
}
