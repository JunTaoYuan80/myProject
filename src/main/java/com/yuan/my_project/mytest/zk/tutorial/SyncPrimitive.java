package com.yuan.my_project.mytest.zk.tutorial;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class SyncPrimitive implements Watcher {
	static ZooKeeper zk = null;
	static Integer mutex;
	String root;
	
	public SyncPrimitive(String address){
		if(zk == null){
			try{
				System.out.println("Starting ZK...");
				zk = new ZooKeeper(address, 3000, this);
				mutex = new Integer(-1);
				System.out.println("Finished starting ZK: "+zk);
			}catch(Exception e){
				System.out.println(e.getMessage());
				zk = null;
			}
		}
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		synchronized(mutex){
			mutex.notify();
		}
	}
	
	
	/**
	 * Barrier
	 */
	public static class Barrier extends SyncPrimitive{
		int size;
		String name;

		public Barrier(String address) {
			super(address);
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * Barrier constructor
		 * @param address
		 * @param root
		 * @param size
		 */
		public Barrier(String address, String root, int size){
			this(address);
			this.root = root;
			this.size = size;
			
			if(zk != null){
				try {
					Stat s = zk.exists(root, false);
					if(s == null){
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//my node name
			try {
				name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		boolean enter() throws KeeperException, InterruptedException{
			zk.create(root+"/"+name, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			
			while(true){
				synchronized(mutex){
					List<String> list = zk.getChildren(root, true);
					if(list.size()<size){
						mutex.wait();
					}else{
						return true;
					}
				}
			}
		}
		
		/**
		 * wait until all reach barrier
		 * @return
		 * @throws InterruptedException
		 * @throws KeeperException
		 */
		boolean leave() throws InterruptedException, KeeperException{
			zk.delete(root+"/"+name, 0);
			while(true){
				List<String> list = zk.getChildren(root, true);
				if(list.size()>size){
					mutex.wait();
				}else{
					return true;
				}
			}
		}
	}
	
	/**
	 * Producer-Consumer queue
	 * @author yuanjuntao
	 *
	 */
	static public class Queue extends SyncPrimitive{

		public Queue(String address) {
			super(address);
			// TODO Auto-generated constructor stub
		}
		
		Queue(String address, String name){
			super(address);
			this.root = name;
			//Create ZK	node name
			if(zk != null){
				try {
					Stat s = zk.exists(root, false);
					if(s == null){
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * Add element to the queue
		 * @param i
		 * @return
		 * @throws KeeperException
		 * @throws InterruptedException
		 */
		boolean produce(int i) throws KeeperException, InterruptedException{
			ByteBuffer b = ByteBuffer.allocate(4);
			byte[] value;
			
			//add children with value i
			b.putInt(i);
			value = b.array();
			zk.create(root+"/element", value, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			
			return true;
		}
		
		/**
		 * Remove the first element from queue
		 * @return
		 * @throws KeeperException
		 * @throws InterruptedException
		 */
		int consume() throws KeeperException, InterruptedException{
			int retvalue = -1;
			Stat stat = null;
			
			//get the first element available
			while(true){
				synchronized(mutex){
					List<String> list = zk.getChildren(root, true);
					if(list.size() == 0){
						System.out.println("Going to wait...");
						mutex.wait();
					}else{
						Integer min = new Integer(list.get(0).substring(7));
						for(String s : list){
							Integer tempValue = new Integer(s.substring(7));
							if(tempValue<min){
								min = tempValue;
							}
						}
						System.out.println("Temporary value: "+root+"/element"+min);
						byte[] b = zk.getData(root+"/element"+min, false, stat);
						zk.delete(root+"/element"+min, 0);
						
						ByteBuffer buffer = ByteBuffer.wrap(b);
						retvalue = buffer.getInt();
						return retvalue;
					}
				}
			}
		}
	}
	
	public static void main(String[] args){
		if(args[0].equals("qTest")){
			queueTest(args);
		}else{
			barrierTest(args);
		}
	}
	
	public static void queueTest(String[] args){
		Queue q = new Queue(args[1], "/temp/app1");
		System.out.println("Input address: "+args[1]);
		Integer max = new Integer(args[2]);
		if(args[3].equalsIgnoreCase("p")){
			System.out.println("Producer...");
			for(int i=0;i<max;i++){
				try {
					q.produce(10+i);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("Consumer...");
			for(int i=0;i<max;i++){
				try {
					int r = q.consume();
					System.out.println("Consumer Item: "+r);
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					--i;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void barrierTest(String[] args){
		Barrier b = new Barrier(args[1], "/temp/b1", new Integer(args[2]));
		
		try {
			boolean flag = b.enter();
			System.out.println("Entered barrier: "+args[2]);
			if(!flag){
				System.out.println("Error when enter barrier...");
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random random = new Random(100);
		int r = random.nextInt();
		for(int i=0;i<r;i++){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
		
		try {
			b.leave();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Left barrier....");
	}
}


