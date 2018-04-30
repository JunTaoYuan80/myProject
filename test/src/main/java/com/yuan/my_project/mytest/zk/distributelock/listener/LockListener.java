package com.yuan.my_project.mytest.zk.distributelock.listener;

/**
 * 锁监听
 * @author yuanjuntao
 *
 */
public abstract class LockListener implements Listener {

	public void execute(){
		System.out.println("rewrite...........");
	}
	
	/**
     * call back called when the lock 
     * is acquired
     */
	public abstract void lockAcquired();
	
	/**
     * call back called when the lock is 
     * released.
     */
	public abstract void lockReleased();
}
