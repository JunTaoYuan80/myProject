package com.yuan.my_project.mytest.zk.distributequeue;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class TestJVMC3 {

	public static void main(String[] args) {
		try {
			ZooKeeper zk = QueueService.connection(QueueService.HOST_ADDRESS);
			Thread.currentThread().setName("TestJVMC3");
			int cnt3 = 0;
			try{
				for (int i = 0; i < 200; i++) {
					ZNode node = Consumer.consume(zk);
					//System.out.println("TestJVMC3 node:"+JSONUtils.valueToString(node));
					if(node !=null){
						cnt3++;
					}
					
					Thread.sleep(new Random().nextInt(2) * 300);
				}
				
				System.out.println("cnt3:"+cnt3);
			}finally{
				if(zk != null){
					zk.close();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
