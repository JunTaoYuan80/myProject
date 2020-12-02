package com.yuan.datastructure;

/**
 * 二分查找的实际运用
 * <p>
 * 问题1：
 * 可可喜欢吃香蕉。这里有N堆香蕉，第i堆中有piles[i]根香蕉。警卫已经离开了，将在H小时后回来。
 * 可可可以决定她吃香蕉的速度K(单位：根/小时)。每个小时，她将会选择一堆香蕉，从中吃掉K根。如果
 * 这堆香蕉少于K根，她将吃掉这堆的所有的香蕉，然后这一小时内不会再吃更多的香蕉。
 * 可可喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
 * 返回她可以在H小时内吃掉所有香蕉的最小速度K（K为整数）
 * </p>
 * <p>
 * 问题2：
 * 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量的顺序往传送带上装载包裹。我们装载的重量不会超过船的最低运载能力。
 * 示例：
 * 输入：weights = [1,2,3,4,5,6,7,8,9,10], D = 5
 * 输出：15
 * 注意：货物必须按照给定顺序装运。
 * </p>
 *
 * @Author yuanjt
 * @Date 2020-12-02 10:10
 **/
public class BinaryFind {
    public static void main(String[] args) {

        int[] piles = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(minEatingSpead(piles, 5));

        System.out.println(shipMinCarry(piles, 5));
    }

    //可可吃香蕉问题, 时间复杂度O(NlogN)
    static int minEatingSpead(int[] piles, int h) {

        int l = 1;
        int r = max(piles) + 1;

        while (l < r) {
            int mid = l + (r - l) / 2;
            if (canFinish(piles, mid, h)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    static boolean canFinish(int[] piles, int s, int h) {
        int time = 0;
        for (int i : piles) {
            time += timeOf(i, s);
        }
        return time <= h;
    }

    static int timeOf(int n, int s) {
        return n / 2 + n % s > 0 ? 1 : 0;
    }

    static int max(int[] piles) {
        int m = 1;
        for (int i : piles) {
            m = Math.max(m, i);
        }

        return m;
    }


    //------------------------------------------------------------------------------------------
    //问题2
    static int shipMinCarry(int[] weights, int d) {
        int l = 1;
        int r = sum(weights) + 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (canCarry(weights, mid, d)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    static boolean canCarry(int[] weights, int w, int d) {
        int day = 0;
        int t = 0;
        for (int i : weights) {
            t += i;
            if (t > w) {
                day++;
                t = i;
            }
        }
        return day < d;
    }

    static boolean canCarry2(int[] w, int d, int cap) {
        int i = 0;
        for (int day = 0; day < d; day++) {
            int maxCap = cap;
            while ((maxCap -= w[i]) > 0) {
                i++;
                if (i == w.length) {
                    return true;
                }
            }
        }
        return false;
    }

    static int sum(int[] weights) {
        int w = 0;
        for (int i : weights) {
            w += i;
        }
        return w;
    }
}
