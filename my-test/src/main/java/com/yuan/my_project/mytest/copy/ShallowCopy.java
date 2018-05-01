package com.yuan.my_project.mytest.copy;

public class ShallowCopy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		T2 t2 = new T2("1111",11);
		T1 t1 = new T1(t2);
		
		T1 t11 = (T1)t1.clone();
		t11.t2.age=22;
		t11.t2.name="222";
		System.out.println(t2 == t11.t2);
		System.out.println("name:"+t2.name+";   age:"+t2.age);
		System.out.println("key=1:"+t11.h.get("1")+";   k=2:"+t11.h.get("2"));
	}

}
