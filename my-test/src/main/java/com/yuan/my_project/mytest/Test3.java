package com.yuan.my_project.mytest;

/**
 * @author yuanjuntao
 * @date 2019/5/10 9:14
 */
public class Test3 {
    public static void main(String[] args) {
        byte x = 10;
        x += 1234;
        System.out.println("x1:" + x);
        //x = x + 1234;

        Object tb = "taobao";
        String ali = "ali";
        tb = tb + ali;
        System.out.println("tb1:" + tb);
        tb += ali;
        System.out.println("tb2:" + tb);
    }
}
