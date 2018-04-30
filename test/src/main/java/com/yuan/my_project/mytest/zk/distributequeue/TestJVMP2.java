package com.yuan.my_project.mytest.zk.distributequeue;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.yuan.my_project.mytest.MyTestException;

public class TestJVMP2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//TODO .......... business handle , then make queue data
			final String data = "data";
			for(int i = 0;i<50;i++){
				final ZooKeeper zk = QueueService.connection(QueueService.HOST_ADDRESS);
				QueueService.initQueue(zk);
				Thread t = new Thread(new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						try{
							for(int j=0;j<5;j++){
								String d1 = Thread.currentThread().getName() + "-" + data + j;
								try {
									Producer.produce(zk, d1);
								} catch (KeeperException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}finally{
							if(zk != null){
								try {
									zk.close();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				});
				t.setName("TestJVMP2");
				t.start();
				
				Thread.sleep(new Random().nextInt(20)*100);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyTestException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeeperException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
