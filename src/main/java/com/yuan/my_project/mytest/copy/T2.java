package com.yuan.my_project.mytest.copy;

public class T2 implements Cloneable {
	String name;
	int age;

	T2(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
