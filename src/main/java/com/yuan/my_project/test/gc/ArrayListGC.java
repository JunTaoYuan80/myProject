package com.yuan.my_project.test.gc;

import java.util.ArrayList;
import java.util.List;

public class ArrayListGC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		allocateMemory();
		try {
			System.out.println("sleep start........");
			Thread.sleep(100000);
			System.out.println("sleep end........");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void allocateMemory(){
		List<byte[]> list = new ArrayList<byte[]>();
		int size = 1024 * 1024 * 500;
		int len = size / (20 * 1024);
		int j = 0;
		for (int i=0;i<len;i++){
			j = i;
			try{
				Thread.sleep(8);
				byte[] bytes = new byte[20 * 1024];
				list.add(bytes);
			}catch(OutOfMemoryError e){
				System.out.println("--------------:"+j);
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("==============:"+j);
	}
}
