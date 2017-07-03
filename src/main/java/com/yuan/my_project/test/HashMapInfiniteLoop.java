package com.yuan.my_project.test;

import java.util.HashMap;

public class HashMapInfiniteLoop {
	private static HashMap<Integer,String> map = new HashMap<Integer,String>();

	public static void main(String[] args){
		map.put(5, "C");
		
		new Thread("thread1"){
			public void run(){
				map.put(7, "B");
			}
		}.start();
		
		new Thread("thread2"){
			public void run(){
				map.put(3,"A");
			}
		}.start();
		
		
		new Thread("thread3"){
			public void run(){
				try {
					System.out.println("thread3 sleep start.....");
					Thread.sleep(3000);
					System.out.println("thread3 sleep end.....");
					System.out.println(map.get(11));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
	}
}
