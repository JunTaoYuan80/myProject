package com.yuan.my_project.mytest.classloaderanalysis;

import java.lang.reflect.Method;

public class TestHotSwap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t = new Thread(new MonitorHotSwap());
		t.start();
	}
	
}

class MonitorHotSwap implements Runnable{
	private String className = "com.yuan.world.mytest.classloaderanalysis.Inner4Test";
	private HotSwapURLClassLoader classLoader = null;
	private Class<?> clazz = null;


	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				initLoader();
				System.out.println("init loader ...");
				Object obj = clazz.newInstance();
				Method m = clazz.getMethod("hot");
				System.out.println("invoke ....");
				m.invoke(obj, null);
				
				Thread.sleep(5000);
				/*Inner4Test t = (Inner4Test)obj;
				t.hot();*/
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void initLoader() throws ClassNotFoundException{
		classLoader = HotSwapURLClassLoader.getClassLoader();
		clazz = classLoader.load(className);
	}
	
}