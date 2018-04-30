package com.yuan.my_project.mytest.zk.leaderfollower;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LeaderElectionLauncher2 {
	public final static String HOST_ADDRESS = "10.108.214.47:2181,10.108.214.50:2181,10.131.119.215:2181";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService service = Executors.newSingleThreadExecutor();
		Thread.currentThread().setName("LeaderElectionLauncher2");
		
		try {
			Future<String> future = service.submit(new ProcessNode(Thread.currentThread().getName(), HOST_ADDRESS));
			try {
				System.out.println(Thread.currentThread().getName()+future.get());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
