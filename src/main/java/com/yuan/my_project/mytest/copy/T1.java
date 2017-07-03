package com.yuan.my_project.mytest.copy;

import java.util.HashMap;
import java.util.Map;

public class T1 implements Cloneable {
	public static String s1 = "hello world";
	public String s2 = "i am t1";
	public Map h = new HashMap();
	T2 t2 = null;
	
	public T1(T2 t2){
		h.put("1", "hi");
		h.put("2", "dddddd");
		this.t2 = t2;
	}
	
	public void m1(){
		System.out.println("hi hello");
	}
	
	public Object clone(){
		T1 o = null;
		
		try {
			o = (T1)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString()); 
		}
		//o.t2 = (T2)t2.clone();
		
		return o;
	}
}
