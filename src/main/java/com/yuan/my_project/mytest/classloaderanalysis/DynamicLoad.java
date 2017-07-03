package com.yuan.my_project.mytest.classloaderanalysis;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 * @author yuanjuntao
 *
 */
public class DynamicLoad {

	private static URLClassLoader ucl;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//load();
		
		//dynamicLoad1();
		
		String innerName = "com.yuan.world.mytest.classloaderanalysis.Inner4Test2";
		String url = System.getProperty("user.dir") + "/target/classes/";
		try {
			System.out.println(url);
			for(int i=0;i<10;i++){
				ucl = new URLClassLoader(new URL[]{new File(url).toURI().toURL()});
				Class<?> c1 = ucl.loadClass(innerName);
				Object obj = c1.newInstance();
				Method method = c1.getMethod("hot");
				method.invoke(obj, null);
				
				Method[] m1 = c1.getDeclaredMethods();
				for(Method m : m1){
					System.out.println(m.getName());
				}
				ucl = null;
				System.out.println("===============dynamic modify class then reload class========== "+i);
				Thread.sleep(5000);
			}
			
			System.out.println("===============dynamic modify class then reload class==========");
			
			ucl = new URLClassLoader(new URL[]{new URL(url)});
			//URLClassLoader ucl2 = new URLClassLoader(new URL[]{new URL(url)},ClassLoader.getSystemClassLoader());
			Class<?> c2 = ucl.loadClass(innerName);
			Method[] m2 = c2.getDeclaredMethods();
			for(Method m : m2){
				System.out.println(m.getName());
			}
			
			
			
		} catch (ClassNotFoundException | IOException | InterruptedException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void dynamicLoad1() {
		String innerName = "com.yuan.world.mytest.classloaderanalysis.Inner4Test";
		try {
			Class<?> clazz1 = Class.forName(innerName);
			Method[] m1 = clazz1.getDeclaredMethods();
			for(Method m : m1){
				System.out.println(m.getName());
			}
			
			System.out.println("===============dynamic modify class then reload class==========");
			
			Class<?> clazz2 = Class.forName(innerName);
			Method[] m2 = clazz2.getDeclaredMethods();
			for(Method m : m2){
				System.out.println(m.getName());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void load(){
		//通过jvm默认的加载器加载classpath路径下的class文件
		String innerName = "com.yuan.world.mytest.classloaderanalysis.Inner4Test";
		try {
			Class<?> clazz = Class.forName(innerName);
			ClassLoader loader = clazz.getClassLoader();
			while(loader != null){
				System.out.println(loader.toString());
				loader = loader.getParent();
			}
			Class<?> clazz2 = Class.forName(innerName);
			System.out.println("they are the same class: "+(clazz==clazz2));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("============================");
		
		//通过URLClassLoader加载器 加载 classpath路径外的class文件
		innerName = "com.yuan.world.mytest.classloaderanalysis.Inner4Test2";
		String url = "file:/" + System.getProperty("user.dir") + "/newClass/";
		try {
			ucl = new URLClassLoader(new URL[]{new URL(url)});
			Class<?> c = ucl.loadClass(innerName);
			ClassLoader l = c.getClassLoader();
			while(l != null){
				System.out.println(l.toString());
				l = l.getParent();
			}
			
			ucl.close();
			
			
			URLClassLoader ucl2 = new URLClassLoader(new URL[]{new URL(url)});
			Class<?> c2 = ucl2.loadClass(innerName);
			System.out.println("222222they are the same class?:"+(c == c2));
			ucl2.close();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
