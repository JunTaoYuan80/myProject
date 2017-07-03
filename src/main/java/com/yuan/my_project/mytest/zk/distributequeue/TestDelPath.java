package com.yuan.my_project.mytest.zk.distributequeue;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class TestDelPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ZooKeeper zk = QueueService.connection(QueueService.HOST_ADDRESS);
			zk.delete(QueueService.QUEUE_ROOT_PATH, -1);
			zk.delete(QueueService.QUEUE_EXIST, -1);
			zk.delete(QueueService.QUEUE_OBTAIN_LOCK, -1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
