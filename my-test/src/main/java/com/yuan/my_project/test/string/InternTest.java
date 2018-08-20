package com.yuan.my_project.test.string;

public class InternTest {

    public static void main(String[] args){
        String s1 = new String("1");
        s1.intern();
        String s2 = "1";
        String s3 = new String("1")+new String("1");
        //String s4 = "11";
        s3.intern();
        String s4 = "11";
        String s5 = "11";

        System.out.println(s3==s4);
        System.out.println(s4==s5);
        System.out.println(s1==s2);

    }

}
