package com.yuan.my_project.test;

import java.util.Arrays;
import java.util.List;

public class IntegerCache {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		collectionTest();
		
	}
	
	public static void intTest(){
		//java.lang.Integer.IntegerCache.high = 256;
		//sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
		Integer i1 = 127;
		Integer i2 = 127;
		System.out.println(i1==i2);
		
		Integer i3 = 300;
		Integer i4 = 300;
		System.out.println(i3==i4);
		
		Integer i5 = 300;
		int i6 = 300;
		System.out.println(i5==i6);
		
		Boolean b1 = true;
		Boolean b2 = true;
		System.out.println(b1==b2);
	}
	
	public static void collectionTest(){
		//asList �ķ��ض�����һ�� Arrays �ڲ��࣬��û��ʵ�ּ��ϵ��޸ķ�����Arrays.asList���ֵ���������ģʽ��ֻ��ת���ӿڣ���̨��������������
		//��һ������� list.add("c"); ����ʱ�쳣��
		//�ڶ�������� str[0]= "gujin"; ��ô list.get(0)Ҳ����֮�޸ġ�
		String[] str = new String[]{"a", "b"};
		List<String> list = Arrays.asList(str);
		
		//list.add("c");
		str[0] = "juntao";
		System.out.println(list.get(0));
	}

}
