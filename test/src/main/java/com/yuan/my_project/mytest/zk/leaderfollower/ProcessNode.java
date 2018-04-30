package com.yuan.my_project.mytest.zk.leaderfollower;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ProcessNode<V> implements Callable<String> {
	
	private final static String LEADER_ELECTION_ROOT_PATH = "/election";
	private final static String PROCESS_NODE_PREFIX = "/e-";
	
	private final String id;
	private final ZooKeeperService zooKeeperService;
	
	private String processNodePath;
	private String watchedNodePath;
	
	public ProcessNode(final String id, String zkURL) throws IOException{
		this.id = id;
		this.zooKeeperService = new ZooKeeperService(zkURL, new ProcessNodeWatcher());
	}
	
	private boolean attempt4LeaderPosition(){
		final List<String> childNodePaths = zooKeeperService.getChildren(LEADER_ELECTION_ROOT_PATH, false);
		Collections.sort(childNodePaths);
		
		//System.out.println("childNodePaths:"+JSONUtils.valueToString(childNodePaths));
		boolean leader = false;
		int idx = childNodePaths.indexOf(processNodePath.substring(processNodePath.lastIndexOf("/")+1));
		if(idx == 0){
			System.out.println(Thread.currentThread().getName()+" I am a leader");
			leader = true;
		}else{
			final String watchedNodeShortPath = childNodePaths.get(idx -1);
			watchedNodePath = LEADER_ELECTION_ROOT_PATH + "/" + watchedNodeShortPath;
			System.out.println("[Process :"+id+"] Setting watch on node with path:"+watchedNodePath);
			zooKeeperService.watchNode(watchedNodePath, true);
		}
		
		return leader;
	}
	

	public String call() throws Exception {
		// TODO Auto-generated method stub
		String rslt = id + " I am a follower";
		System.out.println("Process with id: "+id+" has started!");
		
		final String rootNodePath = zooKeeperService.createNode(LEADER_ELECTION_ROOT_PATH, false, false);
		if(rootNodePath == null){
			throw new IllegalStateException("Unable to create/access leader election root node with path:"+LEADER_ELECTION_ROOT_PATH);
		}
		
		processNodePath = zooKeeperService.createNode(rootNodePath + PROCESS_NODE_PREFIX+id, false, true);
		if(processNodePath == null){
			throw new IllegalStateException("Unable to create/access process node with path:"+LEADER_ELECTION_ROOT_PATH);
		}
		
		System.out.println("[Process :"+id+"] Process node created with path:" + processNodePath);
		if(attempt4LeaderPosition()){
			rslt = id + " I am a leader";
		}
		return rslt;
	}

	
	
	public class ProcessNodeWatcher implements Watcher {

		public void process(WatchedEvent event) {
			// TODO Auto-generated method stub
			System.out.println("watched event:"+event);
			System.out.println("watchedNodePath:"+watchedNodePath+",processNodePath:"+processNodePath);
			EventType type = event.getType();
			if(EventType.NodeDeleted.equals(type) && event.getPath().equalsIgnoreCase(watchedNodePath)){
				attempt4LeaderPosition();
			}
			
		}

	}
}
