package com.yuan.lm.yiang.io;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// t();
		Random random = new Random();
		CaseObject object = new CaseObject();
		boolean result = true;
		while (result) {
			try {
				result = object.execute(random.nextInt(1000));
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void t() {
		int i = 0;
		for (;;) {
			System.out.println(i++);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
