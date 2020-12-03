package com.yuan.datastructure;

/**
 * 如何高效解决接雨水问题
 * <p>针对此题，不要想整体，而应该去想局部</p>
 * a.暴力破解法
 * b.备忘录优化
 * c.双指针解法
 *
 * @Author yuanjt
 * @Date 2020-12-03 10:06
 **/
public class ReceiveRain {


    public static void main(String[] args) {
        int[] h = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trap(h));
        System.out.println(memo(h));
        System.out.println(twoPointor(h));
    }

    /**
     * 双指针 方法
     * 有点：节省空间
     *
     * @param height
     * @return
     */
    static int twoPointor(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int len = height.length;
        int l = 0;
        int r = len - 1;
        int ans = 0;
        //l_max 是 height[0..left]中最高柱子的高度；
        int l_max = height[0];
        //r_max 是 height[right..end]中最高柱子的高度；
        int r_max = height[len - 1];
        while (l <= r) {
            l_max = Math.max(l_max, height[l]);
            r_max = Math.max(r_max, height[r]);
            if (l_max < r_max) {
                ans += l_max - height[l];
                l++;
            } else {
                ans += r_max - height[r];
                r--;
            }
        }

        return ans;
    }


    /**
     * 备忘录
     * <p>
     * 跟暴力解法 很像
     *
     * @param height
     * @return
     */
    static int memo(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int len = height.length;
        int[] l_max = new int[len];
        int[] r_max = new int[len];

        //init
        l_max[0] = height[0];
        r_max[len - 1] = height[len - 1];

        for (int i = 1; i < len; i++) {
            l_max[i] = Math.max(height[i], l_max[i - 1]);
        }

        for (int i = len - 2; i >= 0; i--) {
            r_max[i] = Math.max(height[i], r_max[i + 1]);
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            ans += Math.min(l_max[i], r_max[i]) - height[i];
        }

        return ans;
    }

    /**
     * 暴力破解法
     *
     * @param height
     * @return
     */
    static int trap(int[] height) {
        int n = height.length;
        int ans = 0;

        for (int i = 1; i < n; i++) {
            int l_max = 0;
            int r_max = 0;
            //左边最高柱子
            for (int j = 0; j <= i; j++) {
                l_max = Math.max(l_max, height[j]);
            }
            //右边最高柱子
            for (int j = n - 1; j >= i; j--) {
                r_max = Math.max(r_max, height[j]);
            }

            //最低柱子 - 自身高度
            ans += (Math.min(l_max, r_max) - height[i]);
        }

        return ans;
    }
}
