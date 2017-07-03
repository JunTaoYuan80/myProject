package com.yuan.my_project.mytest;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		t(1);
	}
	
	public static void t(int a){
		System.out.println("dddd");
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0);
		executor.schedule(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("END");
			}
			
		},3, TimeUnit.SECONDS);
	}

}
