package com.yuan.my_project.mytest.zk.distributequeue;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import net.sf.json.util.JSONUtils;

/**
 * 
 * @author yuanjuntao
 *
 */
public class Consumer {

	/**
	 * consumer
	 * @param zk
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public static ZNode consume(ZooKeeper zk) throws KeeperException, InterruptedException{
		/*if(zk.exists(QueueByZK.QUEUE_EXIST, false) == null){
			System.out.println(Thread.currentThread().getName()+" nonode exception");
			//throw new KeeperException.NoNodeException();
			return null;
		}*/
		
		for(;;){
			if(zk.exists(QueueService.QUEUE_OBTAIN_LOCK, false) == null ){
				synchronized(QueueService.lock){
					if(zk.exists(QueueService.QUEUE_OBTAIN_LOCK, false) == null ){
						try{
							zk.create(QueueService.QUEUE_OBTAIN_LOCK, QueueService.getData("lock"), QueueService.getAcl(), CreateMode.EPHEMERAL);
							System.out.println(System.currentTimeMillis()+" get lock");
							break;
						}catch(Exception e){
							System.out.println(System.currentTimeMillis()+" get lock exception:"+e.getMessage());
						}
					}
				}
			}else{
				Thread.sleep(100);
			}
		}
		try{
			List<String> children = zk.getChildren(QueueService.QUEUE_ROOT_PATH, false);
			if(CollectionUtils.isEmpty(children)){
				//zk.delete(QueueByZK.QUEUE_EXIST, -1);
				System.out.println(Thread.currentThread().getName()+" root path--children is empty");
				return null;
			}
			
			SortedSet<ZNode> nodes = new TreeSet<ZNode>();
			for(String id : children){
				nodes.add(new ZNode(id));
			}
			if(CollectionUtils.isNotEmpty(nodes)){
				ZNode node = nodes.first();
				zk.delete(QueueService.QUEUE_ROOT_PATH+"/"+node.getName(), -1);
				System.out.println(Thread.currentThread().getName()+" to do business .................,get node:"+JSONUtils.valueToString(node));
				//Thread.sleep(1000);
				
				return node;
			}
		}finally{
			if(zk.exists(QueueService.QUEUE_OBTAIN_LOCK, false) != null ){
				try{
					zk.delete(QueueService.QUEUE_OBTAIN_LOCK, -1);
					System.out.println(System.currentTimeMillis()+" release lock");
				}catch(Exception e){
					System.out.println(System.currentTimeMillis()+" release lock exception:"+e.getMessage());
				}
			}
		}
		
		return null;
	}
}
