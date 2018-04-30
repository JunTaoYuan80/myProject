package com.yuan.my_project.mytest.zk.distributequeue;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author yuanjuntao
 *
 */
public class Producer {

	/**
	 * produce id
	 * @param zk
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public static void produce(ZooKeeper zk, String data) throws KeeperException, InterruptedException {

		long sessionId = zk.getSessionId();
		String prefix = "q-" + sessionId + "-" + data + "-";
		String id = zk.create(QueueService.QUEUE_ROOT_PATH + "/" + prefix, QueueService.getData("son node"),
				QueueService.getAcl(), CreateMode.PERSISTENT_SEQUENTIAL);
		System.out.println(Thread.currentThread().getName()+" produce node id:"+id);
		
		/*if(zk.exists(QueueByZK.QUEUE_EXIST, false) == null){
			synchronized(QueueByZK.lock){
				if(zk.exists(QueueByZK.QUEUE_EXIST, false) == null){
					String flag = zk.create(QueueByZK.QUEUE_EXIST, "flag".getBytes(), QueueByZK.getAcl(), CreateMode.PERSISTENT);
					System.out.println("QUEUE_EXIST created node:"+flag);
				}
			}
		}*/
	}
}
