package com.yuan.datastructure;

/**
 * 跳跃游戏
 * <p>
 * 1.贪心算法
 * 每一步都计算一下从当前位置最远能够跳到哪里，然后和一个全局最优的最优位置farthest做比对，通过每一步的最优解，更新全局最优解。
 * 2.动态规划
 * 暴力穷举所有可能的跳法，通过备忘录memo消除重叠子问题，取其中的最小值为最终答案
 * </p>
 *
 * @Author yuanjt
 * @Date 2020-12-09 10:00
 **/
public class JumpGame {


    public static void main(String[] args) {

        int[] nums = {2, 3, 1, 1, 4, 0, 3, 2, 1, 5, 2, 1};
        System.out.println(canJump(nums));

        System.out.println(jump(nums));

        System.out.println(jump2(nums));
    }

    /**
     * 贪心算法
     *
     * @param nums
     * @return
     */
    static boolean canJump(int[] nums) {
        if (nums == null) {
            return false;
        }
        int n = nums.length;
        int farthest = 0;
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            //遇到0位置，跳不动
            if (farthest <= i) {
                return false;
            }
        }

        return farthest >= n - 1;
    }

    //备忘录，记录该位置 跳跃的步数，默认值n
    static int[] memo;

    /**
     * 动态规划，求出每一个子问题的最小步数，选择最短路径
     *
     * @param nums
     * @return
     */
    static int jump(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int n = nums.length;
        memo = new int[n];
        //初始化 备忘录
        for (int i = 0; i < n; i++) {
            memo[i] = n;
        }
        return dp(nums, 0);
    }

    /**
     * 贪心算法：贪心选择性质
     * 不需要【递归地】计算出所有选择的具体结果 然后比较求最值，而只需要作出那个最具【潜力】，看起来最优的选择即可。
     *
     * @param nums
     * @return
     */
    static int jump2(int[] nums) {

        int n = nums.length;
        int end = 0;//可以选择跳跃的步数
        int farthest = 0;//记录所有选择i...end中能跳的最远距离
        int jumps = 0;//记录了跳跃的步数
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(nums[i] + i, farthest);
            if (end == i) {
                System.out.println("jump2->i:" + i + ", end:" + end + ", farthest:" + farthest);
                jumps++;
                end = farthest;
            }
        }

        return jumps;
    }

    /**
     * 动态规划
     * <p>
     * 保证你一定可以跳到最后一格，求跳跃的最少步数
     * 时间复杂度O(N^2)，空间复杂度O(N)
     *
     * @param nums
     * @param p    索引下标
     * @return
     */
    static int dp(int[] nums, int p) {
        if (nums == null) {
            return 0;
        }
        int n = nums.length;
        //base case，当超过最后一格时 不需要跳跃
        if (p >= n - 1) {
            return 0;
        }

        //子问题 已经求过解，直接返回该值
        if (memo[p] != n) {
            return memo[p];
        }

        //该p位置能跳跃的步数
        int step = nums[p];
        //可以跳跃 第1步、第2步。。。。
        for (int i = 1; i <= step; i++) {
            //穷举每一个选择，计算每一个子问题的结果
            int subProblem = dp(nums, p + i);
            //当前步数，跟可以跳跃的最优步数，取最小值
            memo[p] = Math.min(memo[p], subProblem + 1);
        }

        return memo[p];
    }
}


