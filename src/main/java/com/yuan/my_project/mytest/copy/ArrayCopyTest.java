package com.yuan.my_project.mytest.copy;

import java.util.ArrayList;
import java.util.List;

public class ArrayCopyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int i = 0;
		List<int[]> list = new ArrayList<int[]>();
		for(;;){
			try {
				i++;
				Thread.sleep(300);
				list.add(new int[1024 * 1024 * 2]);// every time 2M
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				System.out.println(i);
			}
		}
		
	}

}
