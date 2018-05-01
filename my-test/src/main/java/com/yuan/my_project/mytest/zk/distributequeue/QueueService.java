package com.yuan.my_project.mytest.zk.distributequeue;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.ZooKeeper;

import com.yuan.my_project.mytest.MyTestException;

/**
 * 
 * @author yuanjuntao
 *
 */
public class QueueService {
	public final static String QUEUE_ROOT_PATH = "/queue";
	public final static String QUEUE_EXIST = "/dataifexist";
	public final static String QUEUE_OBTAIN_LOCK = "/lock";
	public final static String HOST_ADDRESS = "10.108.214.47:2181,10.108.214.50:2181,10.131.119.215:2181";
	
	public final static Object lock = new Object();

	/**
	 * 连接zk服务
	 * 
	 * @param hosts
	 * @return
	 * @throws IOException
	 */
	public static ZooKeeper connection(String hosts) throws IOException {
		ZooKeeper zk = new ZooKeeper(hosts, 60000, new Watcher() {

			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				if (event != null) {
					System.out.println("event.path:" + event.getPath() + ",event.state:"
							+ event.getState().getIntValue() + ",event.type:" + event.getType().getIntValue());
				}
			}
		});

		return zk;
	}

	/**
	 * 初始化队列
	 * 
	 * @throws InterruptedException
	 * @throws KeeperException
	 * @throws Exception
	 */
	public static void initQueue(ZooKeeper zk) throws MyTestException, KeeperException, InterruptedException {
		if (zk == null) {
			throw new MyTestException("invalid param zk");
		}
		
		if (zk.exists(QUEUE_ROOT_PATH, false) != null) {
			System.out.println("queue has exist!");
		} else {
			zk.create(QUEUE_ROOT_PATH, "task queue".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("queue has created!");
		}
	}
	
	public static byte[] getData(String data){
		return data.getBytes();
	}
	
	public static ArrayList<ACL> getAcl(){
		return Ids.OPEN_ACL_UNSAFE;
	}
}
