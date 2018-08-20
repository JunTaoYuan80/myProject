package com.yuan.my_project.mytest;

import java.util.concurrent.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//t(1);
		//t2();

		int[] a =  {1,3,9,12,14};
		System.out.println(binary(a,0,a.length-1,23));
	}



	public static int binary(int[] a,int min,int max, int b){
		if(a==null || a.length == 0 || min<0 || max>=a.length-1 || max<min){
			return -1;
		}
		int mid = (min+max)/2;
		if(a[mid]>b){
			return binary(a,0,mid-1,b);
		}else if(a[mid]<b){
			return binary(a,mid+1,a.length-1,b);
		}else{
			return mid;
		}
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
