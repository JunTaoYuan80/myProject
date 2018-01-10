package com.yuan.my_project.mytest.zk.leaderfollower;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LeaderElectionLauncher1 {
	public final static String HOST_ADDRESS = "10.108.214.47:2181,10.108.214.50:2181,10.131.119.215:2181";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService service = Executors.newSingleThreadExecutor();
		Thread.currentThread().setName("LeaderElectionLauncher1");
		
		try {
			List<Future<String>> futures = new ArrayList<Future<String>>();
			for(int i=0;i<10;i++){
				Future<String> future = service.submit(new ProcessNode<Object>(Thread.currentThread().getName() + "-" + i, HOST_ADDRESS));
				futures.add(future);
			}
			
			try {
				for(int i=0;i<futures.size();i++){
					System.out.println(futures.get(i).get());
				}
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
