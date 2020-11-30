package com.yuan.datastructure;

import java.util.Arrays;

/**
 * @Author yuanjt
 * @Date 2020-11-30 10:02
 **/
public class Primes {

    public static void main(String[] args) {

        System.out.println("primes count:" + countPrimes(100));
    }

    /**
     * 计算素数
     *
     * @param n
     * @return
     */
    static int countPrimes(int n) {

        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);

        for (int i = 2; i * i < n; i++) {
            if (isPrim[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrim[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrim[i]) {
                count++;
                System.out.println(i);
            }
        }

        return count;
    }
}
