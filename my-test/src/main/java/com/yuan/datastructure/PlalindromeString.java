package com.yuan.datastructure;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * 如何寻找最长回文子串的个数
 * 1.暴力破解法（最差）
 * 2.动态规划（次之）
 * 3.递归法（最优）
 *
 * @Author yuanjt
 * @Date 2020-12-19 15:57
 **/
public class PlalindromeString {

    public static void main(String[] args) {
        System.out.println(maxStr("cbcdabacbabdabcac"));
    }


    /**
     * 递归法（最优）
     *
     * @param s
     * @return
     */
    static String maxStr(String s) {
        if (StringUtils.isBlank(s) || s.length() == 1) {
            return s;
        }
        char[] c = s.toCharArray();
        String res = "";
        int len = 0;
        HashSet<String> set = new HashSet<>();
        for (int i = 1; i < c.length; i++) {
            String s1 = plaindrome(s, i, i);
            String s2 = plaindrome(s, i, i + 1);
            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
            if (res.length() >= len) {
                if (res.length() > len) {
                    set.clear();
                    len = res.length();
                } else {
                    if (!set.contains(res)) {
                        set.add(res);
                    }
                }
            }
        }

        System.out.println("len:" + len + ", count:" + set.size());
        return res;
    }


    static String plaindrome(String s, int l, int r) {
        char[] c = s.toCharArray();
        //防止索引越界
        while (l >= 0 && r < s.length() && c[l] == c[r]) {
            //向两边展开
            l--;
            r++;
        }
        //返回以s[l]和s[r]为中心的最长回文字符串
        int p1 = l + 1;
        int p2 = r;
        p2 = p2 < p1 ? p1 : p2;
        return s.substring(p1, p2);
    }


    /**
     * 暴力破解法
     *
     * @param s
     * @return
     */
    static int maxLength(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }
        char[] arr = s.toCharArray();
        if (arr.length == 1) {
            return 1;
        }
        if (arr.length == 2) {
            if (arr[0] == arr[1]) {
                return 1;
            }
            return 0;
        }
        Map<Integer, Integer> result = new HashMap<>();
        boolean flag = false;
        int len = 0;

        for (int i = 1; i < arr.length; i++) {
            boolean oldEven = true;
            for (int j = 0; j < i; j++) {
                //奇数回文
                if (i + 1 < arr.length && arr[i] != arr[i + 1]) {
                    if (2 * i - j > arr.length - 1) {
                        flag = false;
                        continue;
                    }
                    if (arr[j] != arr[2 * i - j]) {
                        flag = false;
                        continue;
                    }
                } else {
                    oldEven = false;
                    //偶数回文
                    if (2 * i - j + 1 > arr.length - 1) {
                        flag = false;
                        continue;
                    }
                    if (arr[j] != arr[2 * i - j + 1]) {
                        flag = false;
                        continue;
                    }
                }
                flag = true;
                len = (i - j) * 2 + 1;
                if (!oldEven) {
                    len++;
                }

                break;
            }
            if (flag) {
                result.put(len, NumberUtils.toInt(String.valueOf(result.get(len))) + 1);
            }
            flag = false;
        }

        int maxLen = 0;
        int count = 0;
        //取最大长度的个数
        if (result.size() > 0) {
            Iterator<Map.Entry<Integer, Integer>> it = result.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> entry = it.next();
                if (entry.getKey() > maxLen) {
                    maxLen = entry.getKey();
                    count = entry.getValue();
                }
            }
        }
        System.out.println("maxLen:" + maxLen);

        return count;
    }
}
