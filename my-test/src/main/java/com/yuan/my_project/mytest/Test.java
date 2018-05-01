package com.yuan.my_project.mytest;

import java.util.concurrent.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//t(1);
		t2();
	}
	
	public static void t(int a){
		System.out.println("dddd");
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0);
		executor.schedule(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<10;i++) {
					System.out.println("END");
					try {
						//抛异常了，也会继续循环执行
						throw new InterruptedException();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		},3, TimeUnit.SECONDS);
	}


	public static void t2(){
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		Future future = executor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("i test");
			}
		}, "123");
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		System.out.println("abc");

	}

}
