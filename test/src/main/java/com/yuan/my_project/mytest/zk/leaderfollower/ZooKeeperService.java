package com.yuan.my_project.mytest.zk.leaderfollower;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.yuan.my_project.mytest.zk.leaderfollower.ProcessNode.ProcessNodeWatcher;

/**
 * 
 * @author yuanjuntao
 *
 */
public class ZooKeeperService {
	private ZooKeeper zk;
	
	public ZooKeeperService(final String zkURL, final ProcessNodeWatcher watcher) throws IOException{
		zk = new ZooKeeper(zkURL, 6000, watcher);
	}
	
	public String createNode(String node, final boolean watch, final boolean ephimeral){
		String createNodePath = "";
		
		try {
			Stat stat = zk.exists(node, watch);
			if(stat != null){
				createNodePath = node;
			}else{
				createNodePath = zk.create(node, new byte[0], Ids.OPEN_ACL_UNSAFE, (ephimeral?CreateMode.EPHEMERAL_SEQUENTIAL:CreateMode.PERSISTENT));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return createNodePath;
	}
	
	public boolean watchNode(final String node, final boolean watch){
		boolean watchNode = false;
		
		try {
			if(zk.exists(node, watch) != null){
				watchNode = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return watchNode;
	}
	
	public List<String> getChildren(final String node, final boolean watch){
		List<String> children = null;
		try {
			children = zk.getChildren(node, watch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return children;
	}
}
