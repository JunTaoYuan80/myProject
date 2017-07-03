package com.yuan.my_project.mytest.classloaderanalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class HotSwapURLClassLoader extends URLClassLoader {
	//缓存加载class文件，最后修改的时间
	private static Map<String,Long> cachLastModifyTimeMap = new HashMap<String,Long>();
	
	//工程classes根目录
	private static String projectRootPath = "E:/workspaces/myworld/target/classes/";
	//测试类所在路径
	private static String packagePath = "com/yuan/world/mytest/classloaderanalysis/";
	
	private static HotSwapURLClassLoader hcl = new HotSwapURLClassLoader();
	
	public HotSwapURLClassLoader() {
		super(getMyUrls());
		// TODO Auto-generated constructor stub
	}
	
	public static HotSwapURLClassLoader getClassLoader(){
		return hcl;
	}
	
	private static URL[] getMyUrls(){
		
		try {
			URL url = new File(projectRootPath).toURI().toURL();
			
			return new URL[]{url};
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Class<?> load(String name) throws ClassNotFoundException{
		return loadClass(name);
	}
	
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		return loadClass(name, false);
	}
	
	/**
	 * 重写加载类
	 * @throws ClassNotFoundException 
	 */
	@Override
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException{
		//查看HotSwapURLClassLoader实例缓存下，是否已经加载过class
        //不同的HotSwapURLClassLoader实例是不共享缓存的
		Class<?> clazz = findLoadedClass(name);
		if(clazz != null){
			if(resolve){
				resolveClass(clazz);
			}
			
			if(isModify(name)){
				hcl = new HotSwapURLClassLoader();
				clazz = hcl.customClassLoader(name, hcl);
			}
			
			return clazz;
		}
		
		if(!name.startsWith("com.yuan.world")){
			clazz = ClassLoader.getSystemClassLoader().loadClass(name);
			if(clazz != null){
				if(resolve){
					resolveClass(clazz);
				}
				
				return clazz;
			}
		}
		
		return customClassLoader(name, this);
	}
	
	/**
	 * 自定义 类加载器
	 * @param name
	 * @param cl
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Class<?> customClassLoader(String name, ClassLoader cl) throws ClassNotFoundException{
		return customClassLoader(name, false, cl);
	}
	/**
	 * 自定义 类加载器
	 * @param name
	 * @param resolve
	 * @param cl
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Class<?> customClassLoader(String name, boolean resolve, ClassLoader cl) throws ClassNotFoundException {
		Class<?> clazz = null;
		try {
			clazz = ((HotSwapURLClassLoader)cl).findClass(name);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//让父加载器 加载
			clazz = super.loadClass(name, false);
		}
		if(resolve){
			((HotSwapURLClassLoader)cl).resolveClass(clazz);
		}
		
		//缓存最后修改的时间
		cachLastModifyTimeMap.put(name, lastModifyTime(name));
		
		return clazz;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException{
		return super.findClass(name);
	}
	
	/**
	 * 判断文件是否被修改
	 * @param name
	 * @return
	 */
	private boolean isModify(String name){
		long lastTime = lastModifyTime(name);
		long preLastTime = cachLastModifyTimeMap.get(name);
		if(lastTime > preLastTime){
			return true;
		}
		return false;
	}

	/**
	 * 最后修改时间
	 * @param name
	 * @return
	 */
	private long lastModifyTime(String name){
		String path = getClassCompletePath(name);
		File file = new File(path);
		if(!file.exists()){
			throw new RuntimeException(new FileNotFoundException(name));
		}
		
		return file.lastModified();
	}
	
	/**
	 * 获取类的完全路径名
	 * @param name
	 * @return
	 */
	private String getClassCompletePath(String name){
		String clazzName = name.substring(name.lastIndexOf(".")+1);
		return projectRootPath + packagePath + clazzName + ".class";
	}
}
