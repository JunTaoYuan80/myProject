package com.yuan.my_project.test;

import java.util.concurrent.TimeUnit;

public class VolatileTest {
	private volatile static boolean stop;

	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<500;i++){
    		Thread workThread = new Thread(new Runnable() {
    			@Override
    			public void run() {
    				int i = 0;
    				while (!stop) {
    					i++;
    					try {
    						TimeUnit.SECONDS.sleep(1);
    					} catch (InterruptedException e) {
    						e.printStackTrace();
    					}
    				}
    			}
    		});
    		workThread.start();
		}
		TimeUnit.SECONDS.sleep(3);
		stop = true;
	}

}
