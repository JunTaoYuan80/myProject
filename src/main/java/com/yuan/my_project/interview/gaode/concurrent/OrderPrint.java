package com.yuan.my_project.interview.gaode.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 按顺序循环输出A B C值
 * @author yuanjuntao
 *
 */
public class OrderPrint {
	
	CountDownLatch cdl1 = new CountDownLatch(1);
	CountDownLatch cdl2 = new CountDownLatch(1);
	AtomicBoolean con3 = new AtomicBoolean(true);
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OrderPrint op = new OrderPrint();
		op.print();
	}

	public void print(){
		Thread t1 = new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					for(;;){
						if(con3.getAndSet(false)){
							Thread.sleep(1000);
							System.out.println("=====================");
							System.out.println("A");
							//con1.signalAll();
							cdl1.countDown();
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t1.setName("Test1");
		t1.start();
		
		Thread t2 = new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					for(;;){
						//con1.await();
						cdl1.await();
						Thread.sleep(500);
						System.out.println("B");
						cdl1 = new CountDownLatch(1);
						//con2.signalAll();
						cdl2.countDown();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t2.setName("Test2");
		t2.start();
		
		Thread t3 = new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					for(;;){
						//con2.await();
						cdl2.await();
						Thread.sleep(500);
						System.out.println("C");
						cdl2 = new CountDownLatch(1);
						con3.compareAndSet(false, true);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t3.setName("Test3");
		t3.start();
	}
}
