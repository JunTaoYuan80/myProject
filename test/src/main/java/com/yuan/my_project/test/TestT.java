package com.yuan.my_project.test;

import java.util.UUID;

public class TestT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		t2();
	}
	
	public static void t2(){
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		System.out.println(c == d);
		System.out.println(e == f);
		System.out.println(c == (a+b));
		System.out.println(c.equals(a+b));
		System.out.println(g == (a+b));
		System.out.println(g.equals(a+b));
		System.out.println(g.equals(d));
	}
	
	public static void t1(){
		System.out.println(UUID.randomUUID());
		
		final String str = "123";  
        //str = "1234";  
        System.out.println(str);  
  
        final StringBuffer sb = new StringBuffer("123");  
        sb.append("4");  
        System.out.println(sb);
        
        //int a;
        //System.out.println(a);
	}

}
