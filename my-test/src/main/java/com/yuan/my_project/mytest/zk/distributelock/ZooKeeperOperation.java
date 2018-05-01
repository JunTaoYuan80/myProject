package com.yuan.my_project.mytest.zk.distributelock;

import org.apache.zookeeper.KeeperException;

public interface ZooKeeperOperation {
	/**
     * Performs the operation - which may be involved multiple times if the connection
     * to ZooKeeper closes during this operation
     *
     * @return the result of the operation or null
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean execute() throws InterruptedException, KeeperException;
}
