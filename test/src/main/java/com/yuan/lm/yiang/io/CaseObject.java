package com.yuan.lm.yiang.io;

public class CaseObject {
	public int sleepTotalTime = 0;

	public boolean execute(int sleepTime) throws Exception {
		System.out.println("sleep: " + sleepTime);
		sleepTotalTime += sleepTime;
		Thread.sleep(sleepTime);
		return true;
	}
}
