package com.yuan.my_project.test.oom;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class DirectMemoryOOM {
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Field f = Unsafe.class.getDeclaredFields()[0];
		f.setAccessible(true);
		try {
			Unsafe us = (Unsafe)f.get(null);
			
			while(true){
				us.allocateMemory(_1MB);
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
