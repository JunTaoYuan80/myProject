package com.yuan.my_project.mytest.zk.distributequeue;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class TestJVMC2 {

	public static void main(String[] args) {
		try {
			ZooKeeper zk = QueueService.connection(QueueService.HOST_ADDRESS);
			Thread.currentThread().setName("TestJVMC2");
			int cnt2 = 0;
			try{
				for (int i = 0; i < 200; i++) {
					ZNode node = Consumer.consume(zk);
					//System.out.println("TestJVMC2 node:"+JSONUtils.valueToString(node));
					if(node != null){
						cnt2++;
					}
					
					Thread.sleep(new Random().nextInt(3) * 100);
				}
				System.out.println("cnt2:"+cnt2);
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
