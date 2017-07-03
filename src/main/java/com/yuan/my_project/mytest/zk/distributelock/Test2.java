package com.yuan.my_project.mytest.zk.distributelock;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.yuan.my_project.mytest.zk.distributelock.listener.LockListener;

public class Test2 {
	private static final String LOCK_ROOT_NODE = "/testrootpath";
	private static final String ZK_ADDRESS = "10.108.214.47:2181,10.108.214.50:2181,10.131.119.215:2181";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<50;i++){
			Thread thread = new Thread(new Runnable(){
				public void run() {
					// TODO Auto-generated method stub
					try{
						lock.lock();
						//System.out.println(Thread.currentThread().getName() + " getLocalLock........");
						selectLocalLock();
					}finally{
						lock.unlock();
						//System.out.println(Thread.currentThread().getName() + " releaseLocalLock........");
					}
				}
			});
			thread.setName("thread"+i);
			thread.start();
		}
	}
	
	public static void selectLocalLock(){
		ZooKeeper zooKeeper;
		WriteLock writeLock = null;
		try {
			//连接zk集群
			zooKeeper = new ZooKeeper(ZK_ADDRESS, 60000, new Watcher(){
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					System.out.println(Thread.currentThread().getName() + " Watcher fired on path: " + event.getPath() + " state: " + event.getState() + " type "
							+ event.getType());
				}
			});
			
			writeLock = new WriteLock(zooKeeper, LOCK_ROOT_NODE, null, new LockListener(){
				@Override
				public void lockAcquired() {
					// TODO Auto-generated method stub
					System.out.println(Thread.currentThread().getName() + " have locked....^_^...");
				}

				@Override
				public void lockReleased() {
					// TODO Auto-generated method stub
					System.out.println(Thread.currentThread().getName() + " lockReleased  byebye.......");
				}
			});
			
			boolean lockFlag = false;
			int j = 1;
			//循环获取zookeeper的rootpath锁
			//do{
				j++;
				lockFlag = writeLock.lock();
				System.out.println(Thread.currentThread().getName()+" lock-boolean:"+lockFlag);
				if(lockFlag){
					try{
						System.out.println(Thread.currentThread().getName()+" have got lock, id:"+writeLock.getId());
						Thread.sleep(1 * 1000);
						//TO DO.............. business
						
					
					}finally{
						if(writeLock!=null){
							System.out.println(Thread.currentThread().getName()+" unlock end..................." + sdf.format(new Date()));
							writeLock.unlock();
						}
					}
				}
				Thread.sleep(5 * j);
			//}while(!lock);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException...........");
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			System.out.println("KeeperException...........");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("InterruptedException...........");
			e.printStackTrace();
		}
	}

}
